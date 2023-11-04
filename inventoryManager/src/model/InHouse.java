package model;

/**
 * InHouse.java
 */

/**
  * This class initializes InHouse class which is a part type extended to part class
   */

public class InHouse extends Part {
    /**
     * machineid private per UML
     */
    private int machineId;

    /**
     * Constructor for inhouse parts.
     *
     * @param id, name, price, stock, min, max, machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }


    /**
     * getter for part machine ID
     *
     * @return machine Id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter for machine ID
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Temp constructor for associated part management
     *
     * @param partToCopy manages InHouse
     */
    public InHouse(InHouse partToCopy) {
        super(partToCopy.getId(), partToCopy.getName(), partToCopy.getPrice(), partToCopy.getStock(), partToCopy.getMin(), partToCopy.getMax());
        this.machineId = partToCopy.machineId;
    }

    /**
     * filler for tables
     */
    public InHouse() {
        super(00, "Fill", 00, 00, 00, 00);
        this.machineId = 00;
    }


}
