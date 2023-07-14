package com.buct.spider.atcoder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.spider.entity.Acrating;
import com.buct.spider.mapper.AcratingMapper;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class AcRatingPipeline implements Pipeline {

    @Resource
    private AcratingMapper acratingMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> acratingMap = resultItems.getAll();
        for (Map.Entry<String, Object> entry : acratingMap.entrySet()) {
            if (entry.getValue() instanceof Acrating) {
                Acrating acrating = (Acrating) entry.getValue();
                QueryWrapper<Acrating> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("ac_contest_name",acrating.getAcContestName());
                Acrating exist = acratingMapper.selectOne(queryWrapper);
                if (exist == null) {
                    acratingMapper.insert(acrating);
                } else {
                    acratingMapper.update(acrating, queryWrapper);
                }
            }
        }
    }
}
