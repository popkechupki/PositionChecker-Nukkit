package net.comorevi.nukkit.plugin;

import cn.nukkit.math.BlockFace;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.ArrayList;

public class PositionChecker extends PluginBase implements Listener {

    private ArrayList<String> list = new ArrayList<String>();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String username = player.getName();
        if(this.list.contains(username)) {
            String direction = this.directionToString(player.getDirection());
            String levelname = player.getLevel().getFolderName();
            player.sendActionBarTitle(TextFormat.BOLD + (TextFormat.AQUA + "X" + TextFormat.WHITE + ": " + TextFormat.GREEN + player.getFloorX() + TextFormat.AQUA + " Y" + TextFormat.WHITE + ": " + TextFormat.GREEN + (player.getFloorY() - 1) + TextFormat.AQUA + " Z" + TextFormat.WHITE + ": " + TextFormat.GREEN + player.getFloorZ() + TextFormat.YELLOW + " Direction" + TextFormat.WHITE + ": " + TextFormat.GREEN + direction + TextFormat.YELLOW + " World" + TextFormat.WHITE + ": " + TextFormat.GREEN + levelname));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch(command.getName()) {
            case "xyz":
                if(sender instanceof Player) {
                    String username = sender.getName();
                    Player player = getServer().getPlayer(username);
                    if(this.list.contains(username)) {
                        this.list.remove(username);
                        player.sendMessage(TextFormat.AQUA + "PositionChecker>> " + TextFormat.RED + "無効化しました。/Disabled.");
                    } else {
                        this.list.add(username);
                        player.sendMessage(TextFormat.AQUA + "PositionChecker>> " + TextFormat.GREEN + "有効化しました。/Enabled.");
                    }
                }
                break;
        }
        return true;
    }

    public String directionToString(BlockFace direction) {
        switch(direction){
            case SOUTH:
                return "south";
            case WEST:
                return "west";
            case NORTH:
                return "north";
            case EAST:
                return "east";
        }
        return "error";
    }

}
