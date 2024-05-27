package com.daqinzhonggong.sb2.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daqinzhonggong.sb2.admin.domain.AlipayConfig;
import com.daqinzhonggong.sb2.admin.domain.vo.TradeVo;

public interface AliPayService extends IService<AlipayConfig> {

  /**
   * 查询配置
   *
   * @return AlipayConfig
   */
  AlipayConfig find();

  /**
   * 更新配置
   *
   * @param alipayConfig 支付宝配置
   * @return AlipayConfig
   */
  AlipayConfig config(AlipayConfig alipayConfig);

  /**
   * 处理来自PC的交易请求
   *
   * @param alipay 支付宝配置
   * @param trade  交易详情
   * @return String
   * @throws Exception 异常
   */
  String toPayAsPc(AlipayConfig alipay, TradeVo trade) throws Exception;

  /**
   * 处理来自手机网页的交易请求
   *
   * @param alipay 支付宝配置
   * @param trade  交易详情
   * @return String
   * @throws Exception 异常
   */
  String toPayAsWeb(AlipayConfig alipay, TradeVo trade) throws Exception;

}
