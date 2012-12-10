package com.credit.base;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

public interface BaseMapper {

	public <T> List<T> listLimit(RowBounds rows);

	public <T> List<T> search(T bo);

	public <T> List<T> searchLimit(T bo, RowBounds rows);

	public <T> int allCount(T bo);

}
