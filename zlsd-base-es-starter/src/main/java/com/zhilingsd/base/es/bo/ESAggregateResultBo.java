package com.zhilingsd.base.es.bo;

import com.zhilingsd.qi.business.storage.es.model.MediaData;
import com.zhilingsd.qi.business.storage.es.model.TextData;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ^---^---^---^---^---^---^---^
 * --v---v---v---v---v---v---v--
 *
 * @author zou.cp
 * @version 1.0
 * @Description 首页质检录音量，录音时长图表
 * @createTime 2020年02月25日 09:08*
 * log.info()
 */
@ApiModel(value = "质检量BO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ESAggregateResultBo implements Serializable {

  private List<MediaData> mediaDataList;

  private List<TextData> textDataList;
   /**过滤字段对象**/
  private List<Object> resultList;
   /**单组聚合对象**/
  private List<GroupCountRecordBo> groupCountRecordBos;
  /**多组聚合对象**/
  private Map<String,List<GroupCountRecordBo>> groupCountRecordBoListMap = new HashMap<>();

  private Long totalHit;

}
