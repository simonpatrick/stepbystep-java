package com.springdemo.learningMVC.data.src.main.java.com.data.zk.core;

import com.data.zk.serializer.JdkSerializationZkSerializer;
import com.data.zk.serializer.ZkSerializer;
import com.github.zkclient.ZkClient;
import com.github.zkclient.exception.ZkNoNodeException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ZkClientZNodeManager implements ZNodeManager, InitializingBean {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ZkClientZNodeManager.class);
    private static final JdkSerializationZkSerializer JDK_SERIALIZER =
            new JdkSerializationZkSerializer();

    private final Map<String, ZkSerializer<?>> path2Serializer = new HashMap<>();

    private ZkSerializer<Object> defaultSerializer;

    private ZkClient zkClient;

    public ZkClientZNodeManager() {
        super();
    }

    public ZkClientZNodeManager(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public ZkClientZNodeManager(String connectString) {
        this(connectString, ZkClient.DEFAULT_CONNECTION_TIMEOUT);
    }

    public ZkClientZNodeManager(String connectString, int connectionTimeout) {
        this(connectString, ZkClient.DEFAULT_SESSION_TIMEOUT, connectionTimeout);
    }

    public ZkClientZNodeManager(String connectString, int sessionTimeout, int connectionTimeout) {
        this(new ZkClient(connectString, sessionTimeout, connectionTimeout));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (defaultSerializer == null) {
            // 如果默认的序列化器被设置为 null，则要使用JDK序列化。
            defaultSerializer = JDK_SERIALIZER;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setDefaultZkSerializer(ZkSerializer<?> serializer) {
        defaultSerializer = (ZkSerializer<Object>) serializer;
    }

    @Override
    public int countChildren(String path) {
        return zkClient.countChildren(path);
    }

    @Override
    public <T> ZNode<T> create(String nodePath, T data, CreateMode mode, ZkSerializer<T> serializer) {
        byte[] bytes = trySerialize(nodePath, data, serializer);

        // only path
        String actualPath = zkClient.create(nodePath, null, mode);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Create path: {} with {} in mode ({})",
                    actualPath, data, mode);
        }
        Stat stat = zkClient.writeData(nodePath, bytes);
        return new DefaultZNode<>(data, stat, nodePath);
    }

    @Override
    public void createPersistent(String nodePath, boolean createParent) {
        zkClient.createPersistent(nodePath, createParent);
    }

    @Override
    public boolean exists(String nodePath) {
        return zkClient.exists(nodePath);
    }

    @Override
    public boolean delete(String nodePath, boolean isRecursive) {
        if (isRecursive) {
            return zkClient.deleteRecursive(nodePath);
        }
        return zkClient.delete(nodePath);
    }

    @Override
    public boolean delete(String nodePath) {
        return zkClient.delete(nodePath);
    }

    @Override
    public List<ZNode<?>> getChildren(String path, boolean isRecursive) {
        if (!zkClient.exists(path)) {
            return null; // no node
        }
        DefaultZNode<?> pathNode = getNodeInternal(path);
        if (pathNode.getNumChildren() == 0) {
            return ImmutableList.of();
        }
        return getChildren(pathNode, isRecursive);
    }

    @Override
    public List<ZNode<?>> getChildren(ZNode<?> pathNode, boolean isRecursive) {
        Objects.requireNonNull(pathNode, "pathNode");
        List<DefaultZNode<?>> children = getChildrenInternal(pathNode);
        if (isRecursive) {
            children.forEach(child -> {
                // set parent
                child.setParent(pathNode);

                List<ZNode<?>> childrenList = getChildren(child, true);
                if (childrenList == null || childrenList.isEmpty()) {
                    return;
                }
                child.addChildren(childrenList);
            });
        }
        return children.stream()
                .map(Function.identity()).collect(Collectors.toList());
    }

    protected List<DefaultZNode<?>> getChildrenInternal(String path) {
        if (!zkClient.exists(path)) {
            return null; // No such node
        }

        DefaultZNode<?> pathNode = getNodeInternal(path);
        return getChildrenInternal(pathNode);
    }

    protected List<DefaultZNode<?>> getChildrenInternal(ZNode<?> pathNode) {
        Objects.requireNonNull(pathNode, "pathNode");
        List<String> children = zkClient.getChildren(pathNode.getPath());
        if (children == null || children.isEmpty()) {
            return ImmutableList.of();
        }
        List<DefaultZNode<?>> list = Lists.newArrayListWithCapacity(children.size());
        int i = 0;
        String pathPrefix = "/".equals(pathNode.getPath()) ? "" : pathNode.getPath();
        for (String child : children) {
            String actualChildPath = String.format("%s/%s", pathPrefix, child);
            DefaultZNode<?> childNode = getNodeInternal(actualChildPath);
            list.add(i++, childNode);
        }
        return list;
    }

    @Nullable
    @Override
    public List<String> getChildrenPaths(String path) {
        return zkClient.getChildren(path);
    }

    @Override
    public <T> ZNode<T> getNode(String nodePath) {
        byte[] bytes;
        Stat stat = new Stat();
        try {
            bytes = zkClient.readData(nodePath, stat);
        } catch (ZkNoNodeException ex) {
            // no node
            return null;
        }
        if (bytes == null || bytes.length == 0) {
            return new DefaultZNode<>(null, stat, nodePath);
        }
        T data = tryDeserialize(nodePath, bytes, null);
        return new DefaultZNode<>(data, stat, nodePath);
    }

    protected <T> DefaultZNode<T> getNodeInternal(String nodePath) {
        byte[] bytes;
        Stat stat = new Stat();
        try {
            bytes = zkClient.readData(nodePath, stat);
        } catch (ZkNoNodeException ex) {
            // no node
            return null;
        }
        DefaultZNode<T> node;
        if (bytes == null || bytes.length == 0) {
            node = new DefaultZNode<>(null, stat, nodePath);
        } else {
            T data = tryDeserialize(nodePath, bytes, null);
            node = new DefaultZNode<>(data, stat, nodePath);
        }
        return node;
    }

    @Nullable
    @Override
    public List<String> watchForChildren(String nodePath) {
        return zkClient.watchForChilds(nodePath);
    }

    @Override
    public void watchForData(String nodePath) {
        zkClient.watchForData(nodePath);
    }

    @Override
    public <T> ZNode<T> setNodeData(String nodePath, T data, int expectedVersion) {
        byte[] bytes = trySerialize(nodePath, data, null);
        Stat stat = zkClient.writeData(nodePath, bytes, expectedVersion);
        return new DefaultZNode<>(data, stat);
    }

    @SuppressWarnings("unchecked")
    protected <T> byte[] trySerialize(String path, T object, ZkSerializer<T> specialSerializer) {
        if (object == null) {
            return null;
        }
        ZkSerializer<T> serializer;
        boolean useSpecified = false;
        if (specialSerializer != null) {
            serializer = specialSerializer;
            useSpecified = true;
        } else {
            serializer = findSerializer(path);
        }
        if (serializer == null) {
            return this.defaultSerializer.serialize(object);
        }
        byte[] bytes = serializer.serialize(object);
        if (useSpecified) {
            ZkSerializer<T> cacheSerializer = (ZkSerializer<T>) path2Serializer.get(path);
            if (cacheSerializer == null) {
                path2Serializer.put(path, serializer);
            }
        }
        return bytes;
    }

    @SuppressWarnings("unchecked")
    protected <T> T tryDeserialize(String path, byte[] bytes, ZkSerializer<T> defaultSerializer) {
        ZkSerializer<T> serializer;
        boolean useSpecified = false;
        if (defaultSerializer != null) {
            serializer = defaultSerializer;
            useSpecified = true;
        } else {
            serializer = findSerializer(path);
        }
        if (serializer == null) {
            return (T) this.defaultSerializer.deserialize(bytes);
        }
        T t = serializer.deserialize(bytes);
        if (useSpecified) {
            ZkSerializer<T> cacheSerializer = (ZkSerializer<T>) path2Serializer.get(path);
            if (cacheSerializer == null) {
                path2Serializer.put(path, serializer);
            }
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    private <T> ZkSerializer<T> findSerializer(String path) {
        ZkSerializer<T> serializer = (ZkSerializer<T>) path2Serializer.get(path);
        if (serializer == null) {
            int d = path.lastIndexOf('/');
            if (d > 0) {
                return findSerializer(path.substring(0, d));
            }
        }
        return serializer;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public void registerSerializer(String path, ZkSerializer<?> serializer) {
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(serializer, "serializer");
        path2Serializer.put(path, serializer);
    }
}
