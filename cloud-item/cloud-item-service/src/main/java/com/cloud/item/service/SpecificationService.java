package com.cloud.item.service;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.mapper.SpecGroupMapper;
import com.cloud.item.mapper.SpecParamMapper;
import com.cloud.item.pojo.SpecGroup;
import com.cloud.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zf
 * @date 2019-09-30-15:09
 */
@Service
public class SpecificationService {

    @Autowired
    SpecGroupMapper groupMapper;
    @Autowired
    SpecParamMapper paramMapper;

    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> groupList = groupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(groupList)){
            throw new CloudException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return groupList;
    }

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);

        List<SpecParam> paramList = paramMapper.select(specParam);
        if(CollectionUtils.isEmpty(paramList)){
            throw new CloudException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return paramList;
    }

    public List<SpecGroup> queryListByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> groups = queryGroupByCid(cid);
        // 查询组内参数
        List<SpecParam> params = queryParamList(null, cid, null);
        if(CollectionUtils.isEmpty(params)){
            return groups;
        }
        Map<Long, List<SpecParam>> map = new HashMap<>(); //groups.stream().collect(Collectors.toMap(SpecGroup::getId, SpecGroup::getParams));

        for (SpecGroup group : groups) {
            if(null == group.getId() || CollectionUtils.isEmpty(group.getParams())){
                continue;
            }
            map.put(group.getId(),group.getParams());
        }

        for (SpecParam param : params) {
            if(!map.containsKey(param.getGroupId())){
                map.put(param.getGroupId(), new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);
        }
        for (SpecGroup group : groups) {
            group.setParams(map.get(group.getId()));
        }
        return groups;
    }
}
