package com.itdragclick;

import com.itdragclick.Events.LagbackCheck;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import static com.itdragclick.ModClient.*;
import static com.itdragclick.ModClient.debug;

public class ToggleCocoa {
    public static boolean isRunningCocoa = false;
    public static boolean isDoneCocoa = false;
    public static void handleToggle(MinecraftClient client) {
        while (ToggleCocoaBinding.wasPressed()) {
            if(ToggleMelon.isRunningMelon){
                client.player.sendMessage(Text.of(ModName+"§cMelon is still running."));
                client.player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 10.0F, 1.0F);
                return;
            }
            if(ToggleNetherWarts.isRunningWarts){
                client.player.sendMessage(Text.of(ModName+"§cNetherWarts is still running."));
                client.player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 10.0F, 1.0F);
                return;
            }
            if (isRunningCocoa) {
                isRunningCocoa = false;
                isTurning = false;
                isDoneCocoa = false;
                LagbackCheck.isBreakingBlock = false;
                LagbackCheck.inLastLag = false;
                client.player.sendMessage(Text.of(ModName+"§dCocoa §7[§cDisabled§7]"));
                client.player.playSound(SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.MASTER, 100.0F, 1.0F);
                client.options.attackKey.setPressed(false);
                client.options.forwardKey.setPressed(false);
                client.options.leftKey.setPressed(false);
                client.options.rightKey.setPressed(false);
                client.options.backKey.setPressed(false);
            } else {
                LagbackCheck.isBreakingBlock = false;
                isRunningCocoa = true;
                isDoneCocoa = false;
                client.player.sendMessage(Text.of(ModName+"§dCocoa §7[§aEnabled§7]"));
                client.player.playSound(SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.MASTER, 100.0F, 1.0F);
                new Thread(() -> {
                    while (isRunningCocoa) {
                        // repeat 9 times
                        while (isRunningCocoa) {
                            TurnSmoothTo(client, -0, -50);
                            if(client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() == Blocks.GLOWSTONE && !isDoneCocoa){

                                if(debug && client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() == Blocks.GLOWSTONE) System.out.println("Work cuz blockL: "+client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock());
                                isDoneCocoa = true;
                                break;
                            }
                            LagbackCheck.isBreakingBlock = true;
                            client.options.forwardKey.setPressed(true);
//                            client.options.sprintKey.setPressed(true);
                            while (client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() != Blocks.OBSIDIAN && isRunningCocoa) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
//                            client.options.attackKey.setPressed(false);
                            LagbackCheck.isBreakingBlock = false;
                            client.options.leftKey.setPressed(true);
                            client.options.forwardKey.setPressed(true);
                            for (int i = 0; i < 200 && isRunningCocoa; i++) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            client.options.forwardKey.setPressed(false);
                            client.options.leftKey.setPressed(false);
                            TurnSmoothTo(client, -0.0, -50);
                            LagbackCheck.isBreakingBlock = true;
                            client.options.backKey.setPressed(true);
                            while (client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() != Blocks.SEA_LANTERN && isRunningCocoa) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            LagbackCheck.isBreakingBlock = false;
                            client.options.backKey.setPressed(false);
                            client.options.sneakKey.setPressed(true);
                            client.options.leftKey.setPressed(true);
                            for (int i = 0; i < 750 && isRunningCocoa; i++) {
                                try {
                                    Thread.sleep(1);
                                    if(client.player.getWorld().getBlockState(client.player.getBlockPos().down()).getBlock() == Blocks.GLOWSTONE){
                                        if(debug) System.out.println("Block Stepped: "+client.player.getWorld().getBlockState(client.player.getBlockPos().down()).getBlock());
                                        isDoneCocoa = true;
                                        break;
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(!isDoneCocoa) {
                                client.options.leftKey.setPressed(false);
                                client.options.sneakKey.setPressed(false);
                                client.options.rightKey.setPressed(true);
                                client.options.backKey.setPressed(true);
                                for (int i = 0; i < 200 && isRunningCocoa; i++) {
                                    try {
                                        Thread.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                client.options.rightKey.setPressed(false);
                                client.options.backKey.setPressed(false);
                            }
                            if(isDoneCocoa){
                                break;
                            }

                        }
                        // After Repeat Done
                        client.options.sneakKey.setPressed(false);
                        if (debug) client.player.sendMessage(Text.of("Moving to Start Point."));
                        client.options.forwardKey.setPressed(false);
                        client.options.leftKey.setPressed(true);
                        client.options.backKey.setPressed(true);
                        for (int i = 0; i < 750 && isRunningCocoa; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.leftKey.setPressed(false);
                        client.options.backKey.setPressed(false);
                        TurnSmoothTo(client, 90.0, 0);
                        client.options.forwardKey.setPressed(true);
                        client.options.sprintKey.setPressed(true);
                        while (client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() != Blocks.GLOWSTONE && isRunningCocoa) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < 50 && isRunningCocoa; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.forwardKey.setPressed(false);
                        client.options.sprintKey.setPressed(false);
                        TurnSmoothTo(client, -0.0, 0.0);
                        client.options.forwardKey.setPressed(true);
                        client.options.sprintKey.setPressed(true);
                        for (int i = 0; i < 200 && isRunningCocoa; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.sprintKey.setPressed(false);
                        client.options.forwardKey.setPressed(false);
                        client.options.backKey.setPressed(true);
                        client.options.rightKey.setPressed(true);
                        for (int i = 0; i < 250 && isRunningCocoa; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.backKey.setPressed(false);
                        client.options.rightKey.setPressed(false);
                        // Reset
                        LagbackCheck.isBreakingBlock = false;
                        client.options.attackKey.setPressed(false);
                        client.options.backKey.setPressed(false);
                        client.options.rightKey.setPressed(false);
                        client.options.leftKey.setPressed(false);
                        client.options.forwardKey.setPressed(false);
                        isDoneCocoa = false;
                    }
                }).start();
            }
        }
    }
    public static boolean isTurning = false;
    public static void TurnSmoothTo(MinecraftClient client, double targetYaw, double targetPitch) {
        if(!isRunningCocoa){
            isTurning = false;
            return;
        }
        isTurning = true;
        new Thread(() -> {
            while ((Math.abs(MathHelper.wrapDegrees(client.player.getYaw() - targetYaw)) > 0.05 || Math.abs(MathHelper.wrapDegrees(client.player.getPitch() - targetPitch)) > 0.05) && isRunningCocoa) {
//                if(debug) System.out.println("Abs("+targetYaw+" - "+client.player.getYaw()+") == "+Math.abs(targetYaw - client.player.getYaw()));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                client.execute(() -> {
                    double currentYaw = client.player.getYaw();
                    double currentPitch = client.player.getPitch();

                    double deltaYaw = targetYaw - currentYaw;
                    if (deltaYaw < -180) deltaYaw += 360;
                    if (deltaYaw > 180) deltaYaw -= 360;

                    double deltaPitch = targetPitch - currentPitch;

                    client.player.setYaw((float) (currentYaw + deltaYaw * 0.01)); // Adjust speed as needed
                    client.player.setPitch((float) (currentPitch + deltaPitch * 0.01)); // Adjust speed as needed

                    // Ensure pitch doesn't go out of bounds
                    client.player.setPitch(MathHelper.clamp(client.player.getPitch(), -90.0F, 90.0F));
                });
            }
            client.execute(() -> {
                if (debug) client.player.sendMessage(Text.of("Set! Yaw, Pitch"));
                isTurning = false;
            });
        }).start();
        while (isTurning && isRunningCocoa) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
