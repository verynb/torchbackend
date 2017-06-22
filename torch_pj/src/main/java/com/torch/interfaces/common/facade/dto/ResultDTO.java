package com.torch.interfaces.common.facade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页返回结果
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月12日 下午2:20:32 
 *
 */
public class ResultDTO {
	
	   // -- 返回结果 -- //
    private int totalSize; // 总数量
    protected int totalPage; // 总页数
	@JsonProperty("data")
    private List<?> resultList;
    
	 // -- 默认初始化 -- //
    public ResultDTO() {
	    totalSize = 0;
	    totalPage = 0;
	    resultList = new ArrayList<>();
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	public static BuildResultDto buildResultDto(){
		return new BuildResultDto();
	}

	public static class BuildResultDto{
		private int totalSize; // 总数量
		private int totalPage; // 总页数
		private List<?> resultList;

		public BuildResultDto buildTotalSize(int totalSize){
			this.totalSize=totalSize;
			return this;
		}
		public BuildResultDto buildTotalPage(int totalPage){
			this.totalPage=totalPage;
			return this;
		}
		public BuildResultDto buildResultList(List<?> resultList){
			this.resultList=resultList;
			return this;
		}
		public ResultDTO build(){
			ResultDTO resultDTO=new ResultDTO();
			resultDTO.setTotalSize(this.totalSize);
			resultDTO.setTotalPage(this.totalPage);
			resultDTO.setResultList(this.resultList);
			return  resultDTO;
		}
	}
    
}
