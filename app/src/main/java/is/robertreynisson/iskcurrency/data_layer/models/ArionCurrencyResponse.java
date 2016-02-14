package is.robertreynisson.iskcurrency.data_layer.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ArionCurrencyResponse {

    @SerializedName("AskValue")
    @Expose
    private Double AskValue;
    @SerializedName("BidValue")
    @Expose
    private Double BidValue;
    @SerializedName("CustomsRate")
    @Expose
    private Double CustomsRate;
    @SerializedName("LastValueChange")
    @Expose
    private Double LastValueChange;
    @SerializedName("MainTicker")
    @Expose
    private Object MainTicker;
    @SerializedName("MidValue")
    @Expose
    private Double MidValue;
    @SerializedName("Ticker")
    @Expose
    private String Ticker;
    @SerializedName("Time")
    @Expose
    private String Time;
    @SerializedName("Title")
    @Expose
    private String Title;

    /**
     * @return The AskValue
     */
    public Double getAskValue() {
        return AskValue;
    }

    /**
     * @param AskValue The AskValue
     */
    public void setAskValue(Double AskValue) {
        this.AskValue = AskValue;
    }

    /**
     * @return The BidValue
     */
    public Double getBidValue() {
        return BidValue;
    }

    /**
     * @param BidValue The BidValue
     */
    public void setBidValue(Double BidValue) {
        this.BidValue = BidValue;
    }

    /**
     * @return The CustomsRate
     */
    public Double getCustomsRate() {
        return CustomsRate;
    }

    /**
     * @param CustomsRate The CustomsRate
     */
    public void setCustomsRate(Double CustomsRate) {
        this.CustomsRate = CustomsRate;
    }

    /**
     * @return The LastValueChange
     */
    public Double getLastValueChange() {
        return LastValueChange;
    }

    /**
     * @param LastValueChange The LastValueChange
     */
    public void setLastValueChange(Double LastValueChange) {
        this.LastValueChange = LastValueChange;
    }

    /**
     * @return The MainTicker
     */
    public Object getMainTicker() {
        return MainTicker;
    }

    /**
     * @param MainTicker The MainTicker
     */
    public void setMainTicker(Object MainTicker) {
        this.MainTicker = MainTicker;
    }

    /**
     * @return The MidValue
     */
    public Double getMidValue() {
        return MidValue;
    }

    /**
     * @param MidValue The MidValue
     */
    public void setMidValue(Double MidValue) {
        this.MidValue = MidValue;
    }

    /**
     * @return The Ticker
     */
    public String getTicker() {
        return Ticker;
    }

    /**
     * @param Ticker The Ticker
     */
    public void setTicker(String Ticker) {
        this.Ticker = Ticker;
    }

    /**
     * @return The Time
     */
    public String getTime() {
        return Time;
    }

    /**
     * @param Time The Time
     */
    public void setTime(String Time) {
        this.Time = Time;
    }

    /**
     * @return The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

}
