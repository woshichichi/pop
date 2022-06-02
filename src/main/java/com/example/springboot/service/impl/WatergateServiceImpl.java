package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.common.Constants;
import com.example.springboot.common.RoleEnum;
import com.example.springboot.controller.dto.WatergateDTO;
import com.example.springboot.entity.RedisUtil;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Watergate;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.WatergateMapper;
import com.example.springboot.service.IWatergateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2022-05-25
 */
@Service
public class WatergateServiceImpl extends ServiceImpl<WatergateMapper, Watergate> implements IWatergateService {

    private static final Log LOG = Log.get();

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Watergate register(WatergateDTO watergateDTO) {
       Watergate one = getInfo(watergateDTO);
       if(one == null) {
           one = new Watergate();
           BeanUtil.copyProperties(watergateDTO, one, true);
//           redisUtil.getAndSet(Constants.WATERGATE_KEY,"watergate");
           save(one);
       } else {
           throw new ServiceException(Constants.CODE_600,"编号已存在");
       }
        return one;
    }

    private Watergate getInfo(WatergateDTO watergateDTO){
        QueryWrapper<Watergate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bianhao", watergateDTO.getBianhao());
        Watergate one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}
