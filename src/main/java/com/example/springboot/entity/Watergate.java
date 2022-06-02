package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-05-25
 */
@Getter
@Setter
@ToString
@TableName("watergate")
@ApiModel(value = "Watergate对象", description = "")
public class Watergate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("负责人")
    private String fuze;

    @ApiModelProperty("经度")
    private String jingdu;

    @ApiModelProperty("纬度")
    private String weidu;

    @ApiModelProperty("简介")
    private String jianjie;

    @ApiModelProperty("编号")
    private String bianhao;




}
