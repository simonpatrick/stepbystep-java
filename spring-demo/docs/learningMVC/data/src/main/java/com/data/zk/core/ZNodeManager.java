package com.springdemo.learningMVC.data.src.main.java.com.data.zk.core;

import com.data.zk.serializer.ZkSerializer;
import org.apache.zookeeper.CreateMode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface ZNodeManager {

    /**
     * 设置{@code ZNode} 的默认序列化接口实现。
     *
     * @param serializer 默认的序列化接口实现。
     */
    public void setDefaultZkSerializer(ZkSerializer<? extends Object> serializer);

    /**
     * 查找指定节点路径下的子节点数量。
     *
     * @param path 指定的节点路径。
     * @return 返回指定节点路径下的子节点数量。
     * 如果指定的节点不存在，则返回{@code -1}；没有子节点则返回{@code 0}。
     */
    public int countChildren(String path);

    /**
     * 创建一个节点，并写入指定的数据（任何可序列化的对象）。
     *
     * @param nodePath   节点路径。
     * @param data       数据对象。
     * @param mode       创建模式。
     * @param serializer 对象的序列化接口。
     * @param <T>        写的数据类型。
     * @return 返回创建的节点信息。
     */
    public <T> ZNode<T> create(
            String nodePath, T data, CreateMode mode, ZkSerializer<T> serializer);

    /**
     * 创建一个节点，并写入指定的数据（任何可序列化的对象）。
     *
     * @param nodePath   节点路径。
     * @param data       数据对象。
     * @param serializer 指定一个外部的序列化器。
     * @param <T>        写的数据类型。
     * @return 返回创建的节点信息。
     */
    public default <T> ZNode<T> createPersistent(
            String nodePath, T data, ZkSerializer<T> serializer) {
        return create(nodePath, data, CreateMode.PERSISTENT, serializer);
    }

    /**
     * 创建一个节点，并写入指定的数据（任何可序列化的对象）。
     *
     * @param nodePath 节点路径。
     * @param data     数据对象。
     * @param <T>      写的数据类型。
     * @return 返回创建的节点信息。
     */
    public default <T> ZNode<T> createPersistent(String nodePath, T data) {
        return createPersistent(nodePath, data, null);
    }

    /**
     * 创建指定的节点路径。可以指定是否同时创建不存在的父节点路径。
     * <p>
     * 如果有父节点不存在，且{@code createParent = false}，则抛出父节点不存在的异常。
     *
     * @param nodePath     要创建的节点路径。
     * @param createParent 是否同时创建不存在的父节点。
     * @throws com.github.zkclient.exception.ZkNoNodeException
     */
    public void createPersistent(String nodePath, boolean createParent);

    /**
     * 创建指定的节点路径。如果指定路径的父节点路径不存在，则抛出父节点不存在的异常。
     *
     * @param nodePath 要创建的节点路径。
     * @throws com.github.zkclient.exception.ZkNoNodeException
     */
    public default void createPersistent(String nodePath) {
        createPersistent(nodePath, false);
    }

    /**
     * 创建一个临时的（会话级）指定路径和数据的节点。
     *
     * @param nodePath 节点路径。
     * @param data 节点数据。
     * @param serializer 序列化器。
     * @param <T> 节点数据类型。
     * @return 返回新创建的节点信息。
     * @throws com.github.zkclient.exception.ZkException 如果发生错误（节点已存在、超时等）。
     */
    public default <T> ZNode<T> createEphemeral(
            String nodePath, T data, ZkSerializer<T> serializer) {
        return create(nodePath, data, CreateMode.EPHEMERAL, serializer);
    }

    /**
     * 创建一个临时的指定数据的节点。
     *
     * @param nodePath 节点路径。
     * @param data     节点数据。
     * @param <T>      节点数据类型。
     * @return 返回创建的节点信息。
     * @throws com.github.zkclient.exception.ZkException 如果发生错误（节点已存在、超时等）。
     */
    public default <T> ZNode<T> createEphemeral(String nodePath, T data) {
        return createEphemeral(nodePath, data, null);
    }

    /**
     * 创建一个临时的空数据的指定路径的节点。
     *
     * @param nodePath 节点路径。
     * @throws com.github.zkclient.exception.ZkException 如果发生错误（节点已存在、超时等）。
     */
    public default void createEphemeral(String nodePath) {
        createEphemeral(nodePath, null, null);
    }

    /**
     * 检查指定的节点路径是否存在。
     *
     * @param nodePath 要检查的节点路径。
     * @return 如果节点路径存在，则返回{@code true}，否则{@code false}。
     */
    public boolean exists(String nodePath);

    /**
     * 删除指定的节点路径。如果{@code isRecursive = true}，则同时删除所有的子节点。
     *
     * @param nodePath    要删除的节点路径。
     * @param isRecursive 是否递归删除子节点。
     * @return 如果删除成功则返回{@code true}，否则{@code false}。
     * @throws com.github.zkclient.exception.ZkException 如果指定路径含有子节点({@code isRecursive=false})。
     */
    public boolean delete(String nodePath, boolean isRecursive);

    /**
     * 删除指定的节点路径。如果指定的节点路径不存在，则直接返回{@code true}。
     *
     * @param nodePath 要删除的节点路径。
     * @return 如果删除成功则返回{@code true}，否则{@code false}。
     * @throws com.github.zkclient.exception.ZkException 如果指定路径含有子节点
     */
    public boolean delete(String nodePath);

    /**
     * 查找所有一级节点。
     *
     * @param isRecursive 是否递归查找子节点。
     * @return 返回所有一组节点。
     */
    @Nonnull
    public default List<ZNode<?>> topList(boolean isRecursive) {
        return getChildren("/", isRecursive);
    }

    /**
     * 查指定路径的子节点的信息集合。
     *
     * @param path 指定的路径。
     * @return 返回指定路径的子节点的信息集合。
     * 返回{@code null} 表示指定的节点不存在，返回空列表，表示指定的节点存在，但没有子节点。
     */
    @Nullable
    public default List<ZNode<?>> getChildren(String path) {
        return getChildren(path, false);
    }

    public List<ZNode<?>> getChildren(String path, boolean isRecursive);

    public List<ZNode<?>> getChildren(ZNode<?> pathNode, boolean isRecursive);

    /**
     * 查找指定路径的子节点的路径集合。
     *
     * @param path 指定的路径。
     * @return 返回指定路径的子节点路径集合。
     * 返回{@code null} 表示指定的节点不存在，返回空列表，表示指定的节点存在，但没有子节点。
     */
    @Nullable
    public List<String> getChildrenPaths(String path);

    /**
     * 查询指定路径的节点信息。
     *
     * @param nodePath 指定的节点路径。
     * @param <T>      节点数据类型。
     * @return 返回指定路径的节点信息。如果指定的路径不存在，则返回{@code null}。
     */
    public <T> ZNode<T> getNode(String nodePath);

    /**
     * 监听指定节点的所有子节点。
     *
     * @param nodePath 指定的节点路径。
     * @return 返回子节点的路径集合。
     * 返回{@code null} 表示指定的节点不存在，返回空列表，表示指定的节点存在，但没有子节点。
     */
    @Nullable
    public List<String> watchForChildren(String nodePath);

    /**
     * 监听指定的节点路径。
     *
     * @param nodePath 指定的节点路径。
     */
    public void watchForData(String nodePath);

    /**
     * 设置指定节点路径（路径必须存在）下的预期版本的数据。
     *
     * @param nodePath        节点路径。
     * @param data            数据对象。
     * @param expectedVersion 预期的版本号。
     * @param <T>             要设置的节点数据类型。
     * @return 返回节点信息。
     * @throws com.github.zkclient.exception.ZkException 如果指定节点不存在，或者版本号不存在
     */
    public <T> ZNode<T> setNodeData(String nodePath, T data, int expectedVersion);

    /**
     * 设置指定节点路径的数据。
     *
     * @param nodePath 节点路径。
     * @param data     数据对象。
     * @param <T>      要设置的节点数据类型。
     * @return 返回节点信息。
     * @throws com.github.zkclient.exception.ZkException 如果指定节点不存在，或者版本号不存在
     */
    public default <T> ZNode<T> setNodeData(String nodePath, T data) {
        return setNodeData(nodePath, data, -1);
    }
}
