package com.github.tavlima.spotippos.domain.request;

import com.github.tavlima.spotippos.constraint.GreaterThan;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by thiago on 8/4/16.
 */
@GreaterThan.List({
        @GreaterThan(target = "bx", reference = "ax", message = "bx must be greater than ax"),
        @GreaterThan(target = "by", reference = "ay", message = "by must be greater than ay")
})
public class FindByRegionPayload {

    @Min(0)
    @Max(1400)
    @NotNull
    private Integer ax;

    @Min(0)
    @Max(1000)
    @NotNull
    private Integer ay;

    @Min(0)
    @Max(1400)
    @NotNull
    private Integer bx;

    @Min(0)
    @Max(1000)
    @NotNull
    private Integer by;

    public Integer getAx() {
        return ax;
    }

    public void setAx(Integer ax) {
        this.ax = ax;
    }

    public Integer getAy() {
        return ay;
    }

    public void setAy(Integer ay) {
        this.ay = ay;
    }

    public Integer getBx() {
        return bx;
    }

    public void setBx(Integer bx) {
        this.bx = bx;
    }

    public Integer getBy() {
        return by;
    }

    public void setBy(Integer by) {
        this.by = by;
    }
}
