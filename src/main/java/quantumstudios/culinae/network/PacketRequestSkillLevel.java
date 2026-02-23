package quantumstudios.culinae.network;

import java.util.List;
import java.util.stream.Collectors;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quantumstudios.culinae.api.CulinarySkill;
import quantumstudios.culinae.api.CulinarySkillManager;
import quantumstudios.culinae.api.util.SkillUtil;
import snownee.kiwi.network.PacketMod;

public class PacketRequestSkillLevel implements PacketMod
{

    @Override
    public void writeDataTo(ByteBuf byteBuf)
    {
    }

    @Override
    public void readDataFrom(ByteBuf byteBuf)
    {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClient(EntityPlayerSP entityPlayerSP)
    {

    }

    @Override
    public void handleServer(EntityPlayerMP entityPlayerMP)
    {
        List<String> skillPoints = CulinarySkillManager.getSkills().stream().filter((
                e
        ) -> SkillUtil.hasPlayerLearnedSkill(entityPlayerMP, e)).map(CulinarySkill::getName).collect(Collectors.toList());
        // TODO send packet to client
    }
}
