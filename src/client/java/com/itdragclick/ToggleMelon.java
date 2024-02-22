package com.itdragclick;

import com.itdragclick.Events.LagbackCheck;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import static com.itdragclick.ModClient.*;

public class ToggleMelon {
    public static boolean isRunningMelon = false;
    public static boolean isDoneMelon = false;
    public static void handleToggle(MinecraftClient client) {
        while (ToggleMelonBinding.wasPressed()) {
            if(ToggleCocoa.isRunningCocoa){
                client.player.sendMessage(Text.of(ModName+"§cCocoa is still running."));
                client.player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 10.0F, 1.0F);
                return;
            }
            if(ToggleNetherWarts.isRunningWarts){
                client.player.sendMessage(Text.of(ModName+"§cNetherWarts is still running."));
                client.player.playSound(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 10.0F, 1.0F);
                return;
            }
            if (isRunningMelon) {
                isRunningMelon = false;
                isTurning = false;
                isDoneMelon = false;
                LagbackCheck.isBreakingBlock = false;
                LagbackCheck.inLastLag = false;
                client.player.sendMessage(Text.of(ModName+"§dMelon §7[§cDisabled§7]"));
                client.player.playSound(SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.MASTER, 100.0F, 1.0F);
                client.options.attackKey.setPressed(false);
                client.options.forwardKey.setPressed(false);
                client.options.leftKey.setPressed(false);
                client.options.rightKey.setPressed(false);
                client.options.backKey.setPressed(false);
            } else {
                LagbackCheck.isBreakingBlock = false;
                isRunningMelon = true;
                isDoneMelon = false;
                client.player.sendMessage(Text.of(ModName+"§dMelon §7[§aEnabled§7]"));
                client.player.playSound(SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.MASTER, 100.0F, 1.0F);
                new Thread(() -> {
                    while (isRunningMelon) {
                        // repeat 9 times
                        while (isRunningMelon) {
                            TurnSmoothTo(client, -45.0, 45);
                            if(client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() == Blocks.COARSE_DIRT && !isDoneMelon){

                                if(debug && client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() == Blocks.COARSE_DIRT) System.out.println("Work cuz blockL: "+client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock());
                                isDoneMelon = true;
                                break;
                            }
                            client.options.forwardKey.setPressed(true);
                            client.options.rightKey.setPressed(true);
//                            client.options.attackKey.setPressed(true);
                            LagbackCheck.isBreakingBlock = true;
                            client.options.sprintKey.setPressed(true);
                            while (client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() != Blocks.OBSIDIAN && isRunningMelon) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
//                            client.options.attackKey.setPressed(false);
                            LagbackCheck.isBreakingBlock = false;
                            client.options.forwardKey.setPressed(false);
                            client.options.rightKey.setPressed(false);
                            client.options.sprintKey.setPressed(false);
                            TurnSmoothTo(client, -90.0, 45);
                            client.options.forwardKey.setPressed(true);
                            for (int i = 0; i < 550 && isRunningMelon; i++) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            client.options.forwardKey.setPressed(false);
                            client.options.leftKey.setPressed(true);
                            client.options.backKey.setPressed(true);
                            for (int i = 0; i < 450 && isRunningMelon; i++) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            client.options.leftKey.setPressed(false);
                            client.options.backKey.setPressed(false);
                            TurnSmoothTo(client, -135.0, 45);
                            client.options.forwardKey.setPressed(true);
                            client.options.leftKey.setPressed(true);
//                            client.options.attackKey.setPressed(true);
                            LagbackCheck.isBreakingBlock = true;
                            client.options.sprintKey.setPressed(true);
                            while (client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() != Blocks.OBSIDIAN && isRunningMelon) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
//                            client.options.attackKey.setPressed(false);
                            LagbackCheck.isBreakingBlock = false;
                            client.options.forwardKey.setPressed(false);
                            client.options.leftKey.setPressed(false);
                            client.options.sprintKey.setPressed(false);
                            for (int i = 0; i < 100 && isRunningMelon; i++) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            TurnSmoothTo(client, -90.0, 45);
                            client.options.forwardKey.setPressed(true);
                            for (int i = 0; i < 750 && isRunningMelon; i++) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            client.options.forwardKey.setPressed(false);
                            client.options.rightKey.setPressed(true);
                            client.options.backKey.setPressed(true);
                            for (int i = 0; i < 350 && isRunningMelon; i++) {
                                try {
                                    Thread.sleep(1);
                                    if(client.player.getWorld().getBlockState(client.player.getBlockPos().down()).getBlock() == Blocks.COARSE_DIRT){
                                        if(debug) System.out.println("Block Stepped: "+client.player.getWorld().getBlockState(client.player.getBlockPos().down()).getBlock());
                                        isDoneMelon = true;
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            client.options.rightKey.setPressed(false);
                            client.options.backKey.setPressed(false);
                            TurnSmoothTo(client, -45.0, 45);
                            if(isDoneMelon){
                                break;
                            }
                        }
                        // After Repeat 9 times
                        client.options.rightKey.setPressed(true);
                        for (int i = 0; i < 50 && isRunningMelon; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.rightKey.setPressed(false);
                        client.options.forwardKey.setPressed(true);
                        client.options.rightKey.setPressed(true);
//                        client.options.attackKey.setPressed(true);
                        LagbackCheck.isBreakingBlock = true;
                        client.options.sprintKey.setPressed(true);
                        while (isRunningMelon) {
                            if(client.player.getWorld().getBlockState(client.player.getBlockPos().down()).getBlock() == Blocks.SEA_LANTERN){
                                if(debug) System.out.println("Block Stepped: "+client.player.getWorld().getBlockState(client.player.getBlockPos().down()).getBlock());
                                break;
                            }
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (debug) client.player.sendMessage(Text.of("Moving to Start Point."));
                        client.options.forwardKey.setPressed(false);
                        client.options.sprintKey.setPressed(false);
//                        client.options.attackKey.setPressed(false);
                        LagbackCheck.isBreakingBlock = false;
                        client.options.rightKey.setPressed(false);
                        client.options.leftKey.setPressed(false);
                        TurnSmoothTo(client, 90.0, 45.0);
                        client.options.forwardKey.setPressed(true);
                        client.options.sprintKey.setPressed(true);
                        while (client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() != Blocks.OBSIDIAN && isRunningMelon) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.forwardKey.setPressed(false);
                        client.options.sprintKey.setPressed(false);
                        TurnSmoothTo(client, 180.0, 45.0);
                        client.options.forwardKey.setPressed(true);
                        client.options.sprintKey.setPressed(true);
                        while (client.player.clientWorld.getBlockState(client.player.getBlockPos().down()).getBlock() != Blocks.SEA_LANTERN && isRunningMelon) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.forwardKey.setPressed(false);
                        client.options.sprintKey.setPressed(false);
                        for (int i = 0; i < 100 && isRunningMelon; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.rightKey.setPressed(true);
                        client.options.forwardKey.setPressed(true);
                        for (int i = 0; i < 500 && isRunningMelon; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        client.options.rightKey.setPressed(false);
                        client.options.leftKey.setPressed(true);
                        for (int i = 0; i < 250 && isRunningMelon; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        LagbackCheck.isBreakingBlock = false;
                        client.options.attackKey.setPressed(false);
                        client.options.backKey.setPressed(false);
                        client.options.rightKey.setPressed(false);
                        client.options.leftKey.setPressed(false);
                        client.options.forwardKey.setPressed(false);
                        for (int i = 0; i < 100 && isRunningMelon; i++) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        isDoneMelon = false;
                    }
                }).start();
            }
        }
    }
    public static boolean isTurning = false;
    public static void TurnSmoothTo(MinecraftClient client, double targetYaw, double targetPitch) {
        if(!isRunningMelon){
            isTurning = false;
            return;
        }
        isTurning = true;
        new Thread(() -> {
            while ((Math.abs(MathHelper.wrapDegrees(client.player.getYaw() - targetYaw)) > 0.05 || Math.abs(MathHelper.wrapDegrees(client.player.getPitch() - targetPitch)) > 0.05) && isRunningMelon) {
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
        while (isTurning && isRunningMelon) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
