package io.api.etherscan.model;

import io.api.etherscan.util.BasicUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 28.10.2018
 */
public class Block {

    private long blockNumber;
    private BigInteger blockReward;
    private String timeStamp;
    private String _timeStamp;

    //<editor-fold desc="Getter">
    public long getBlockNumber() {
        return blockNumber;
    }

    public String  getTimeStamp() {
        if(_timeStamp == null && !BasicUtils.isEmpty(timeStamp)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = new Date(1000L * Long.valueOf(timeStamp));
            _timeStamp = sdf.format(date);
//            _timeStamp = LocalDateTime.ofEpochSecond(Long.valueOf(timeStamp), 0, ZoneOffset.UTC);
        }
        return _timeStamp;
    }

    public BigInteger getBlockReward() {
        return blockReward;
    }
    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        return blockNumber == block.blockNumber;
    }

    @Override
    public int hashCode() {
        return (int) (blockNumber ^ (blockNumber >>> 32));
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockNumber=" + blockNumber +
                ", blockReward=" + blockReward +
                ", timeStamp='" + timeStamp + '\'' +
                ", _timeStamp=" + _timeStamp +
                '}';
    }
}
