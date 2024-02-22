package com.itdragclick.Events;

import com.google.common.eventbus.Subscribe;
import com.itdragclick.ModClient;
import com.itdragclick.ToggleCocoa;
import com.itdragclick.ToggleMelon;
import com.itdragclick.ToggleNetherWarts;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static com.itdragclick.ModClient.ModName;
import static com.itdragclick.ModClient.debug;
import static com.itdragclick.ToggleCocoa.isRunningCocoa;
import static com.itdragclick.ToggleMelon.isRunningMelon;
import static com.itdragclick.ToggleNetherWarts.isRunningWarts;

public class LagbackCheck {
    private Vec3d lastTickPos = null;
    public static boolean inLastLag = false;
    public static boolean isBreakingBlock = false;

    public LagbackCheck() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            onTick(client);
            if(isRunningMelon || isRunningCocoa) {
                if (client.player.getMainHandStack().getItem() == Items.DIAMOND_AXE ||
                        client.player.getMainHandStack().getItem() == Items.GOLDEN_AXE ||
                        client.player.getMainHandStack().getItem() == Items.IRON_AXE
                ) {
//                    if (debug) System.out.println("Using Axe.");
                } else {
                    if (debug) System.out.println("Not using Axe!");
                    int slot = getAxeSlot(client);
                    if (slot == -1 && (isRunningMelon || isRunningCocoa)) {
                        isRunningMelon = false;
                        isRunningCocoa = false;
                        inLastLag = true;
                        ToggleMelon.isTurning = false;
                        ToggleCocoa.isTurning = false;
                        ToggleMelon.isDoneMelon = false;
                        ToggleCocoa.isDoneCocoa = false;
                        MinecraftClient.getInstance().player.sendMessage(Text.of(ModName + "§dMacro §7[§cERROR: x1§7]"));
                        MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.VOICE, 100.0F, 2.0F);
                        MinecraftClient.getInstance().options.attackKey.setPressed(false);
                        MinecraftClient.getInstance().options.forwardKey.setPressed(false);
                        MinecraftClient.getInstance().options.leftKey.setPressed(false);
                        MinecraftClient.getInstance().options.rightKey.setPressed(false);
                        MinecraftClient.getInstance().options.backKey.setPressed(false);
                        inLastLag = false;
                    }else{
                        if (debug) System.out.println("Set use slot Axe!");
                        client.player.getInventory().selectedSlot = slot;
                    }
                }
            }
            if(isRunningWarts) {
                if (client.player.getMainHandStack().getItem() == Items.DIAMOND_HOE ||
                        client.player.getMainHandStack().getItem() == Items.GOLDEN_HOE ||
                        client.player.getMainHandStack().getItem() == Items.IRON_HOE
                ) {
//                    if (debug) System.out.println("Using Axe.");
                } else {
                    if (debug) System.out.println("Not using Axe!");
                    int slot = getHoeSlot(client);
                    if (slot == -1 && (isRunningWarts)) {
                        isRunningWarts = false;
                        inLastLag = true;
                        ToggleNetherWarts.isTurning = false;
                        ToggleNetherWarts.isDoneWarts = false;
                        MinecraftClient.getInstance().player.sendMessage(Text.of(ModName + "§dMacro §7[§cERROR: x1§7]"));
                        MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.VOICE, 100.0F, 2.0F);
                        MinecraftClient.getInstance().options.attackKey.setPressed(false);
                        MinecraftClient.getInstance().options.forwardKey.setPressed(false);
                        MinecraftClient.getInstance().options.leftKey.setPressed(false);
                        MinecraftClient.getInstance().options.rightKey.setPressed(false);
                        MinecraftClient.getInstance().options.backKey.setPressed(false);
                        inLastLag = false;
                    }else{
                        if (debug) System.out.println("Set use slot Hoe!");
                        client.player.getInventory().selectedSlot = slot;
                    }
                }
            }
            if(isRunningWarts || isRunningCocoa || isRunningMelon){
                // Check for IsAttack
                client.options.attackKey.setPressed(isBreakingBlock && !inLastLag);
            }
        });
    }
    public int getAxeSlot(MinecraftClient client) {
        for (int i = 0; i < 9; i++) { // The hotbar has 9 slots, indexed from 0 to 8
            ItemStack itemStack = client.player.getInventory().getStack(i);
            if (itemStack.getItem() == Items.DIAMOND_AXE || itemStack.getItem() == Items.GOLDEN_AXE || itemStack.getItem() == Items.IRON_AXE) {
                return i;
            }
        }
        return -1; // Return -1 if the player does not have a diamond axe in their hotbar
    }
    public int getHoeSlot(MinecraftClient client) {
        for (int i = 0; i < 9; i++) { // The hotbar has 9 slots, indexed from 0 to 8
            ItemStack itemStack = client.player.getInventory().getStack(i);
            if (itemStack.getItem() == Items.DIAMOND_HOE || itemStack.getItem() == Items.GOLDEN_HOE || itemStack.getItem() == Items.IRON_HOE) {
                return i;
            }
        }
        return -1; // Return -1 if the player does not have a diamond axe in their hotbar
    }
    @Subscribe
    public void onTick(MinecraftClient client) {
        if (client.player != null) {
            if (lastTickPos != null) {
                Vec3d currentPos = client.player.getPos();
                double distance = lastTickPos.distanceTo(currentPos);
                if ((isRunningMelon || isRunningCocoa || isRunningWarts) && !inLastLag && distance > 20) {
                    inLastLag = true;
                    isRunningMelon = false;
                    isRunningCocoa = false;
                    isRunningWarts = false;
                    ToggleMelon.isTurning = false;
                    ToggleCocoa.isTurning = false;
                    ToggleNetherWarts.isTurning = false;
                    ToggleMelon.isDoneMelon = false;
                    ToggleCocoa.isDoneCocoa = false;
                    ToggleNetherWarts.isDoneWarts = false;
                    MinecraftClient.getInstance().player.sendMessage(Text.of(ModName + "§dMacro §7[§cERROR: x1STA§7]"));
                    MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.VOICE, 10.0F, 2.0F);
                    MinecraftClient.getInstance().options.attackKey.setPressed(false);
                    MinecraftClient.getInstance().options.forwardKey.setPressed(false);
                    MinecraftClient.getInstance().options.leftKey.setPressed(false);
                    MinecraftClient.getInstance().options.rightKey.setPressed(false);
                    MinecraftClient.getInstance().options.backKey.setPressed(false);
                    inLastLag = false;
                }else if ((isRunningMelon || isRunningCocoa || isRunningWarts) && !inLastLag && distance > 0.85) {
                    inLastLag = true;
                    new Thread(() -> {
                        if(ModClient.debug) System.out.println("LagBack!");
                        boolean w = client.options.forwardKey.isPressed();
                        boolean s = client.options.backKey.isPressed();
                        boolean a = client.options.leftKey.isPressed();
                        boolean d = client.options.rightKey.isPressed();
                        boolean attack = client.options.attackKey.isPressed();
                        boolean sprint = client.options.sprintKey.isPressed();
                        if(ModClient.debug){
                            System.out.println(w);
                            System.out.println(s);
                            System.out.println(a);
                            System.out.println(d);
                            System.out.println(attack);
                            System.out.println(sprint);
                            System.out.println("----------------------------------");
                            System.out.println(client.options.forwardKey.isPressed());
                            System.out.println(client.options.backKey.isPressed());
                            System.out.println(client.options.leftKey.isPressed());
                            System.out.println(client.options.rightKey.isPressed());
                            System.out.println(client.options.attackKey.isPressed());
                            System.out.println(client.options.sprintKey.isPressed());
                        }
                        if(ModClient.debug) System.out.println("Setting Pressed = false");
                        client.options.forwardKey.setPressed(false);
                        client.options.backKey.setPressed(false);
                        client.options.leftKey.setPressed(false);
                        client.options.rightKey.setPressed(false);
                        client.options.attackKey.setPressed(false);
                        client.options.sprintKey.setPressed(false);
                        if(ModClient.debug) System.out.println("Set! Pressed = false");
                        for (int i = 0; i < 500 && (isRunningMelon || isRunningCocoa || isRunningWarts); i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        double playerX = client.player.getX();
                        double playerY = client.player.getY();
                        double playerZ = client.player.getZ();
                        // Create a BlockPos object for the block below the player
                        BlockPos blockPos = new BlockPos((int) playerX, (int) (playerY - 0.1), (int) playerZ);
                        // Get the block at the player's feet
                        Block block = client.world.getBlockState(blockPos).getBlock();
                        // Check if the player is not stepping on dirt, coarse dirt, or sea lantern
                        if (block != Blocks.DIRT &&
                                block != Blocks.COARSE_DIRT &&
                                block != Blocks.GRASS_BLOCK &&
                                block != Blocks.STONE_BRICKS &&
                                block != Blocks.OAK_PLANKS &&
                                block != Blocks.GLOWSTONE &&
                                block != Blocks.OAK_STAIRS &&
                                block != Blocks.MELON &&
                                block != Blocks.ATTACHED_MELON_STEM &&
                                block != Blocks.FARMLAND &&
                                block != Blocks.MELON_STEM &&
                                block != Blocks.OBSIDIAN &&
                                block != Blocks.STONE_BRICK_STAIRS &&
                                block != Blocks.SPRUCE_SLAB &&
                                block != Blocks.OAK_TRAPDOOR &&
                                block != Blocks.WATER &&
                                block != Blocks.AIR &&
                                block != Blocks.SEA_LANTERN)
                        {
                            ToggleMelon.isRunningMelon = false;
                            isRunningWarts = false;
                            ToggleMelon.isTurning = false;
                            ToggleNetherWarts.isTurning = false;
                            ToggleMelon.isDoneMelon = false;
                            ToggleCocoa.isRunningCocoa = false;
                            ToggleCocoa.isTurning = false;
                            ToggleCocoa.isDoneCocoa = false;
                            ToggleNetherWarts.isDoneWarts = false;
                            inLastLag = false;
                            MinecraftClient.getInstance().player.sendMessage(Text.of(ModName + "§dMacro §7[§cERROR: x0STA§7]"));
                            Identifier blockID = Registries.BLOCK.getId(block);
                            MinecraftClient.getInstance().player.sendMessage(Text.of(ModName + "§dBlock §7[§c" + blockID.getPath() + "§7]"));
                            MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.VOICE, 10.0F, 1.0F);
                            MinecraftClient.getInstance().options.attackKey.setPressed(false);
                            MinecraftClient.getInstance().options.forwardKey.setPressed(false);
                            MinecraftClient.getInstance().options.leftKey.setPressed(false);
                            MinecraftClient.getInstance().options.rightKey.setPressed(false);
                            MinecraftClient.getInstance().options.backKey.setPressed(false);
                            return;
                        }
                        if(ModClient.debug) System.out.println("done!");
                        if(isRunningMelon || isRunningCocoa || isRunningWarts){
                            client.options.forwardKey.setPressed(w);
                            client.options.backKey.setPressed(s);
                            client.options.leftKey.setPressed(a);
                            client.options.rightKey.setPressed(d);
                            client.options.attackKey.setPressed(attack);
                            client.options.sprintKey.setPressed(sprint);
                        }
                        if(ModClient.debug) System.out.println("Set! Pressed = last true");
                    }).start();
                    inLastLag = false;
                }
            }
            lastTickPos = client.player.getPos();
        }
    }
}
