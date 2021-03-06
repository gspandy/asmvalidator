package com.github.bingoohuang.asmvalidator.asm;

import java.util.concurrent.atomic.AtomicInteger;

public class LocalIndices {
    private AtomicInteger localIndex; // 当前变量索引

    private int originalLocalIndex = 1; // 原始本地变量索引
    private boolean originalPrimitive; // 原始变量是否是原生类型
    private int stringLocalIndex = 1; // 转换为string的本地变量索引
    private int isNullIndex; // 空判断布尔本地变量索引

    public LocalIndices(AtomicInteger localIndex) {
        this.localIndex = localIndex;
    }

    public int getOriginalLocalIndex() {
        return originalLocalIndex;
    }

    public int getStringLocalIndex() {
        return stringLocalIndex;
    }


    public int getIsNullIndex() {
        return isNullIndex;
    }

    public int getLocalIndex() {
        return localIndex.get();
    }

    public void incrementAndSetOriginalLocalIndex() {
        this.originalLocalIndex = localIndex.incrementAndGet();
        this.stringLocalIndex = this.originalLocalIndex;
    }

    public void incrementAndSetStringLocalIndex() {
        this.stringLocalIndex = localIndex.incrementAndGet();
    }

    public void incrementAndSetNullLocalIndex() {
        this.isNullIndex = localIndex.incrementAndGet();
    }

    public int incrementLocalIndex() {
        return this.localIndex.incrementAndGet();
    }

    public void setOriginalLocalIndex(int originalLocalIndex) {
        this.originalLocalIndex = originalLocalIndex;
    }

    public boolean isOriginalPrimitive() {
        return originalPrimitive;
    }

    public void setOriginalPrimitive(boolean originalPrimitive) {
        this.originalPrimitive = originalPrimitive;
    }
}
