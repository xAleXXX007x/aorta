package msifeed.mc.aorta;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import msifeed.mc.aorta.attributes.AttributeHandler;
import msifeed.mc.aorta.chat.Speechat;
import msifeed.mc.aorta.client.GuiHandler;
import msifeed.mc.aorta.commands.AortaCommand;
import msifeed.mc.aorta.config.ConfigManager;
import msifeed.mc.aorta.core.Core;
import msifeed.mc.aorta.defines.DefineCommand;
import msifeed.mc.aorta.defines.Defines;
import msifeed.mc.aorta.genesis.Genesis;
import msifeed.mc.aorta.genesis.rename.RenameCommand;
import msifeed.mc.aorta.tweaks.EnableDesertRain;
import msifeed.mc.aorta.tweaks.EntityControl;
import msifeed.mc.aorta.tweaks.MakeEveryoneHealthy;
import msifeed.mc.aorta.tweaks.MakeFoodEdible;
import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;

public class Aorta {
    public static final String MODID = "aorta";
    public static final String NAME = "Aorta";
    public static final String VERSION = "@VERSION@";

    public static Defines DEFINES = new Defines();

    @SidedProxy(
            serverSide = "msifeed.mc.aorta.core.Core",
            clientSide = "msifeed.mc.aorta.core.CoreClient"
    )
    public static Core CORE;

    @SidedProxy(
            serverSide = "msifeed.mc.aorta.genesis.Genesis",
            clientSide = "msifeed.mc.aorta.genesis.GenesisClient"
    )
    public static Genesis GENESIS;

    @SidedProxy(
            serverSide = "msifeed.mc.aorta.client.GuiHandler",
            clientSide = "msifeed.mc.aorta.client.GuiHandlerClient"
    )
    public static GuiHandler GUI_HANDLER;

    private Speechat speechat = new Speechat();
    private EntityControl entityControl = new EntityControl();

    public void preInit(FMLPreInitializationEvent event) {
        ConfigManager.init(event);
        AttributeHandler.init();
    }

    public void init() {
        CORE.init();
        GENESIS.init();

        speechat.init();

        MakeEveryoneHealthy.apply();
    }

    public void postInit() {
        EnableDesertRain.apply();
        MakeFoodEdible.apply();
    }

    public void serverStarting(FMLServerStartedEvent event) {
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        final CommandHandler commandHandler = (CommandHandler) server.getCommandManager();

        commandHandler.registerCommand(new AortaCommand());
        commandHandler.registerCommand(new DefineCommand());
        commandHandler.registerCommand(new RenameCommand());

        CORE.registerCommands(commandHandler);
        speechat.registerCommands(commandHandler);
    }
}