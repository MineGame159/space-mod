package minegame159.spacemod.api;

public interface ResourceSlot<R> {
    R getResource();

    void setResource(R resource, int amount);

    int getAmount();

    int getCapacity();

    boolean canInsert(ResourceInteraction interaction);

    boolean canExtract(ResourceInteraction interaction);

    int insert(R resource, int maxAmount, ResourceInteraction interaction);

    int extract(R resource, int maxAmount, ResourceInteraction interaction);
}
