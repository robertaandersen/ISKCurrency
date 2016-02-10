package is.robertreynisson.iskcurrency.data_layer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CurrencyResponse {

    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

//-----------------------------------is.robertreynisson.iskcurrency.data_layer.models.Result.java-----------------------------------


    @Generated("org.jsonschema2pojo")
    public class Result {

        @SerializedName("shortName")
        @Expose
        private String shortName;
        @SerializedName("longName")
        @Expose
        private String longName;
        @SerializedName("value")
        @Expose
        private Double value;
        @SerializedName("askValue")
        @Expose
        private Integer askValue;
        @SerializedName("bidValue")
        @Expose
        private Integer bidValue;
        @SerializedName("changeCur")
        @Expose
        private Double changeCur;
        @SerializedName("changePer")
        @Expose
        private Double changePer;

        /**
         * @return The shortName
         */
        public String getShortName() {
            return shortName;
        }

        /**
         * @param shortName The shortName
         */
        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        /**
         * @return The longName
         */
        public String getLongName() {
            return longName;
        }

        /**
         * @param longName The longName
         */
        public void setLongName(String longName) {
            this.longName = longName;
        }

        /**
         * @return The value
         */
        public Double getValue() {
            return value;
        }

        /**
         * @param value The value
         */
        public void setValue(Double value) {
            this.value = value;
        }

        /**
         * @return The askValue
         */
        public Integer getAskValue() {
            return askValue;
        }

        /**
         * @param askValue The askValue
         */
        public void setAskValue(Integer askValue) {
            this.askValue = askValue;
        }

        /**
         * @return The bidValue
         */
        public Integer getBidValue() {
            return bidValue;
        }

        /**
         * @param bidValue The bidValue
         */
        public void setBidValue(Integer bidValue) {
            this.bidValue = bidValue;
        }

        /**
         * @return The changeCur
         */
        public Double getChangeCur() {
            return changeCur;
        }

        /**
         * @param changeCur The changeCur
         */
        public void setChangeCur(Double changeCur) {
            this.changeCur = changeCur;
        }

        /**
         * @return The changePer
         */
        public Double getChangePer() {
            return changePer;
        }

        /**
         * @param changePer The changePer
         */
        public void setChangePer(Double changePer) {
            this.changePer = changePer;
        }
    }
}