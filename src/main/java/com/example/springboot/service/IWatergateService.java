package com.example.springboot.service;

import com.example.springboot.controller.dto.WatergateDTO;
import com.example.springboot.entity.Watergate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2022-05-25
 */
public interface IWatergateService extends IService<Watergate> {

    Watergate register(WatergateDTO watergateDTO);
}
