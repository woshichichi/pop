package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONUtil;

import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.WatergateDTO;
import com.example.springboot.entity.RedisUtil;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Watergate;
import com.example.springboot.service.IWatergateService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.springboot.common.Constants.WATERGATE_KEY;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/watergate")
public class WatergateController {

    @Resource
    private IWatergateService watergateService;



    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    private final String now = DateUtil.now();

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Watergate watergate) {
//        String name = watergate.getName();
//        if (watergate.getId() == null) {
//            //watergate.setTime(DateUtil.now());
//            //watergate.setUser(TokenUtils.getCurrentUser().getUsername());
//        }

        watergateService.saveOrUpdate(watergate);

        flushRedis(Constants.WATERGATE_KEY);

//        String json = stringRedisTemplate.opsForValue().get(Constants.WATERGATE_KEY);
//        Page<Watergate> page1;
//
//        page1 = JSONUtil.toBean(json, new TypeReference<Page<Watergate>>() {
//        }, true);
//

        return Result.success();
    }

    @PostMapping("/register")
//    @CachePut(value = "watergate", key = "'watergate'")
    public Result register(@RequestBody WatergateDTO watergateDTO) {
//        String name = watergate.getName();
//        if (watergate.getId() == null) {
//            //watergate.setTime(DateUtil.now());
//            //watergate.setUser(TokenUtils.getCurrentUser().getUsername());
//        }

        flushRedis(Constants.WATERGATE_KEY);



        return Result.success(watergateService.register(watergateDTO));
    }

//    @CacheEvict(value = "watergate",key = "#id")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        watergateService.removeById(id);

        flushRedis(WATERGATE_KEY);

        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        watergateService.removeByIds(ids);

        flushRedis(WATERGATE_KEY);

        return Result.success();
    }

    @GetMapping
    public Result findAll() {

        System.out.println("8");
        return Result.success(watergateService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        System.out.println("0");
        return Result.success(watergateService.getById(id));
    }

    @GetMapping("/page")
//    @Cacheable(value = "watergate",key = "'watergate'")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        System.out.println("7");
        QueryWrapper<Watergate> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
            System.out.println("ads");
        }
//        User currentUser = TokenUtils.getCurrentUser();
//        if (currentUser.getRole().equals("ROLE_USER")) {
//            queryWrapper.eq("user", currentUser.getUsername());
//        }
        //去数据
        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.WATERGATE_KEY);
        Page<Watergate> page;
        if (StrUtil.isBlank(jsonStr)) {
            page = watergateService.page(new Page<>(pageNum, pageSize), queryWrapper);
            stringRedisTemplate.opsForValue().set(Constants.WATERGATE_KEY,JSONUtil.toJsonStr(page), 30L, TimeUnit.MINUTES);
        } else {
            page = JSONUtil.toBean(jsonStr, new TypeReference<Page<Watergate>>() {
            }, true);
        }

//        String v = redisUtil.get(Constants.WATERGATE_KEY);
//        Page<Watergate> page;
//        if (StrUtil.isBlank(v)) {
//            page = watergateService.page(new Page<>(pageNum, pageSize), queryWrapper);
////            stringRedisTemplate.opsForValue().set(Constants.WATERGATE_KEY,JSONUtil.toJsonStr(page));
//            redisUtil.set(Constants.WATERGATE_KEY, "watergate");
//        } else {
////            page = JSONUtil.toBean(v, new TypeReference<Page<Watergate>>() {
////            }, true);
////           page = redisUtil.get(Constants.WATERGATE_KEY);
//            page = JSONUtil.toBean(v, new TypeReference<Page<Watergate>>() {
//           }, true);
//        }

//        String v = redisUtil.get(WATERGATE_KEY);
//        Page<Watergate> page;
//        if(StrUtil.isBlank(v)) {
//            page = watergateService.page(new Page<>(pageNum, pageSize), queryWrapper);
//            redisUtil.set(WATERGATE_KEY, "watergate");
//        }else {
//            page = JSONUtil.toBean(v, new TypeReference<Page<Watergate>>() {
//            },true);
//        }
        return Result.success(page);
    }

    /**
    * 导出接口
    */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        System.out.println("6");
        // 从数据库查询出所有的数据
        List<Watergate> list = watergateService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Watergate信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

        }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Watergate> list = reader.readAll(Watergate.class);

        watergateService.saveBatch(list);
        return Result.success();
    }

//    private User getUser() {
//        return TokenUtils.getCurrentUser();
//    }

    //删除缓存
    private void flushRedis(String key) {
        stringRedisTemplate.delete(key);
    }

    private void setCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

}

