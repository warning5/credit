package com.credit.mapper.rate;

import java.util.List;

public interface DicMapper<T> {
    
    List<T> selectAll();
}