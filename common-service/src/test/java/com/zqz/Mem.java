package com.zqz;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

import java.time.Instant;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: Mem
 * @Date: Created in 10:11 2022/3/8
 */
@Measurement(name = "mem")
public class Mem {

    @Column(tag = true)
    String host;
    @Column
    Double usedPercent;
    @Column(timestamp = true)
    Instant time;
}
