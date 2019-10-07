package com.cloud.order.client;

import com.cloud.order.dto.AddressDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zf
 * @date 2019-10-07-12:54
 */
public class AddressClient {

    public static final List<AddressDTO> addressList = new ArrayList<AddressDTO>(){
        {
            AddressDTO address = new AddressDTO();
            address.setId(1L);
            address.setAddress("和平路12号  3号楼");
            address.setCity("长沙");
            address.setDistrict("雨花区");
            address.setName("张三");
            address.setPhone("139");
            address.setState("长沙");
            address.setZipCode("21000");
            address.setIsDefault(true);
            add(address);

            AddressDTO address2 = new AddressDTO();
            address2.setId(2L);
            address2.setAddress("雷锋路12号  3号楼");
            address2.setCity("北京");
            address2.setDistrict("昌平区");
            address2.setName("李四");
            address2.setPhone("139");
            address2.setState("北京");
            address2.setZipCode("41000");
            address2.setIsDefault(false);
            add(address2);
        }
    };


    public static AddressDTO findById(Long id){
        return addressList.stream().filter(el -> el.getId() == id).findFirst().get();
    }


}
