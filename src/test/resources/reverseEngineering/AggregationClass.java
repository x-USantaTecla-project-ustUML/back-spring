import java.util.HashMap;

class AggregationClass{

    private List<Aggregation> aggregation;
    private AggregationMethod[] aggregationMethod;
    private HashMap<String, AggregationHashMap> aggregationHashMap;

    AggregationClass(List<Aggregation> aggregation, AggregationMethod[] aggregationMethod,
                     HashMap<String, AggregationHashMap> aggregationHashMap){
        this.aggregation = aggregation;
        this.aggregationMethod = this.setAggregationMethod();
    }

    void setAggregationMethod(){
        this.aggregationMethod = aggregationMethod;
    }

}