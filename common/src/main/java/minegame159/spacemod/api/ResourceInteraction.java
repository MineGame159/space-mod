package minegame159.spacemod.api;

public enum ResourceInteraction {
    Automatic,
    Manual,
    Simulation;

    public enum Mask {
        None,
        Automatic,
        Manual,
        Both;

        public boolean allows(ResourceInteraction interaction) {
            return switch (interaction) {
                case Automatic -> this == Automatic || this == Both;
                case Manual -> this == Manual || this == Both;
                case Simulation -> this != None;
            };
        }
    }
}
