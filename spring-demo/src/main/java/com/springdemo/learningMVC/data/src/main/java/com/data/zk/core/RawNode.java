package com.springdemo.learningMVC.data.src.main.java.com.data.zk.core;

import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class RawNode extends AbstractZNode<byte[]> {

    private static final long serialVersionUID = -2802213545310452267L;

    public RawNode(byte[] data, Stat stat) {
        super(data, stat);
    }

    @Override
    public ZNode<byte[]> getParent(){
        return (ZNode<byte[]>) super.getParent();
    }

    @Override
    public void setParent(ZNode<?> parent){
        //parameter checking
        if (parent != null && parent.getData() != null) {
            checkArgument(parent.getData() instanceof byte[], "parent data must be raw byte[]");
        }

        super.setParent(parent);
    }

    /**
     * 返回所有的子节点。
     *
     * 子节点中的{@code data} 数据一定是{@code byte[]}。
     */
    @Override
    public List<ZNode<?>> getChildren() {
        return super.getChildren();
    }

    /**
     * 设置子节点。
     *
     * @param children 子节点。
     * @throws IllegalArgumentException 如果子节点中的数据出现非{@code byte[]}类型。
     */
    @Override
    public void setChildren(Set<ZNode<?>> children) {
        if (children != null && children.size() > 0) {
            children.forEach(z -> checkArgument(
                    z.getData() instanceof byte[], "child data must be raw byte[]"));
        }
        super.setChildren(children);
    }
}
