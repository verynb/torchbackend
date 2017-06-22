package com.torch.interfaces.common.command;

/**
 * Created by bici on 2017/1/12.
 */
public class PageCommand {
    private Integer page; // 起始页数 - 当前页数
    private Integer size; // 大小 - 每页包含的条数
    private String sortWay;
    private String order;
    private String condition;

 // -- 默认初始化 -- //
    public PageCommand() {
        page=1;
        size = 100;
        sortWay = "id";
        order = "desc";
    }

    public void initPage(int page){
        this.page=page;
    }
    public void initSize(int size){
        this.size=size;
    }

    public String getCondition() {
        return condition;
    }

    public PageCommand setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public int getOffset(){
        try{
            int offset=(getPage()-1)*getSize();
            return offset;
        }catch (Exception e){
            return 0;
        }
    }
    public boolean isNotNull() {
        return this.page > 0 && this.size >0;
    }
    

    public Integer getPage() {
        return page;
    }

    public PageCommand setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public PageCommand setSize(Integer size) {
        this.size = size;
        return this;
    }

    public String getSortWay() {
        return sortWay;
    }

    public PageCommand setSortWay(String sortWay) {
        this.sortWay = sortWay;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public PageCommand setOrder(String order) {
        this.order = order;
        return this;
    }

    public static  PageCommandBuilder pageCommandBuilder(){
        return new PageCommandBuilder();
    }

    public static class PageCommandBuilder{
        private Integer page; // 起始页数 - 当前页数
        private Integer size; // 大小 - 每页包含的条数
        private String sortWay;
        private String order;
        private String condition;

        public PageCommandBuilder(){
            page = 1;
            size = 100;
            sortWay = "id";
            order = "desc";
        }

        public PageCommandBuilder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public PageCommandBuilder withSize(Integer size) {
            this.size = size;
            return this;
        }

        public PageCommandBuilder withSortWay(String sortWay) {
            this.sortWay = sortWay;
            return this;
        }

        public PageCommandBuilder withOrder(String order) {
            this.order = order;
            return this;
        }

        public PageCommandBuilder withCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public PageCommand build(){
            PageCommand pageCommand = new PageCommand();
            pageCommand.setOrder(this.order);
            pageCommand.setPage(this.page);
            pageCommand.setSize(this.size);
            pageCommand.setSortWay(this.sortWay);
            return  pageCommand;
        }
    }
}
