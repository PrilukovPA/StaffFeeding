package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientActivator {

    @JsonProperty("is_activated_by_distributor")
    private final boolean isActivatedByDistributor;

    public ClientActivator(boolean isActivatedByDistributor) {
        this.isActivatedByDistributor = isActivatedByDistributor;
    }
}
