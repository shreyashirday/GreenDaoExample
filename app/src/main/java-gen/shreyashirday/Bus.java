package shreyashirday;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table BUS.
 */
public class Bus {

    private Long id;
    private String route;

    public Bus() {
    }

    public Bus(Long id) {
        this.id = id;
    }

    public Bus(Long id, String route) {
        this.id = id;
        this.route = route;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
