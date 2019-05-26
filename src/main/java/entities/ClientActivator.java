package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс предназначен для преобразования запроса на активацию в формат JSON
 */
public class ClientActivator {

    @JsonProperty("is_activated_by_distributor")
    private final boolean isActivatedByDistributor;

    public ClientActivator(boolean isActivatedByDistributor) {
        this.isActivatedByDistributor = isActivatedByDistributor;
    }
}
