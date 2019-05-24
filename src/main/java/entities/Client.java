package entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "hills_clients")
public class Client {
    @JsonProperty("id")
    @Id
    private Integer id;
    @JsonProperty("email")
    @Column
    private String email;
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;
    @JsonProperty("middle_name")
    @Column(name = "middle_name")
    private String middleName;
    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;
    @JsonProperty("clinic")
    @Transient
    private Clinic clinic;
    @JsonProperty("is_activated_by_distributor")
    @Column(name = "is_activated_by_distributor")
    private Boolean isActivatedByDistributor;
    @JsonProperty("phone")
    @Column(name = "phone")
    private String phone;

    public Integer getId() {
        return id;
    }

    public Boolean getActivatedByDistributor() {
        return isActivatedByDistributor;
    }

    public Client() { }
}

class Clinic {
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("delivery_id")
    private String deliveryId;
    @JsonProperty("client_id")
    private String clientId;

    public Clinic() { }
}

