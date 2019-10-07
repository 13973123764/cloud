package com.cloud.search.repository;

import com.cloud.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zf
 * @date 2019-10-03-14:51
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
