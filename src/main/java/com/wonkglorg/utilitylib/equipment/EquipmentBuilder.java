package com.wonkglorg.utilitylib.equipment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wonkglorg
 * <p>
 * <p>
 * This class  constructs and updates a LivingEntity's equipment using an {@link EntityEquipment} object.
 */
@SuppressWarnings("unused")
@ThreadSafe
public final class EquipmentBuilder{
	private LivingEntity livingEntity;
	private final Map<@NotNull EquipmentSlot, @NotNull EquipmentItem> equipmentMap = new ConcurrentHashMap<>();
	private boolean silent = true;
	private final Object lock = new Object();
	
	/**
	 * Constructs a new {@code EquipmentBuilder}.
	 */
	public EquipmentBuilder() {
		
	}
	
	/**
	 * Constructs a new {@code EquipmentBuilder} with the given {@code LivingEntity}.
	 *
	 * @param livingEntity the {@link LivingEntity} to update.
	 */
	public EquipmentBuilder(@NotNull LivingEntity livingEntity) {
		this.livingEntity = Objects.requireNonNull(livingEntity);
	}
	
	/**
	 * Updates the {@link LivingEntity}'s equipment with the equipment set using this builder.
	 * <p>
	 * If the {@code livingEntity} has not been set, this method will return null.
	 *
	 * @return the updated {@link LivingEntity}.
	 */
	public synchronized LivingEntity build() {
		EntityEquipment equipment = livingEntity.getEquipment();
		if(equipment == null){
			return livingEntity;
		}
		for(EquipmentSlot equipmentSlot : equipmentMap.keySet()){
			EquipmentItem equipmentItem = equipmentMap.get(equipmentSlot);
			equipment.setItem(equipmentSlot, equipmentMap.get(equipmentSlot).getItemStack(), silent);
			setDropChance(equipmentSlot, equipmentItem.getDropChance());
		}
		
		return livingEntity;
	}

	private void setDropChance(EquipmentSlot equipmentSlot, float dropChance) {
		switch (equipmentSlot) {
			case HAND:
				livingEntity.getEquipment().setItemInMainHandDropChance(dropChance);
				break;
			case OFF_HAND:
				livingEntity.getEquipment().setItemInOffHandDropChance(dropChance);
				break;
			case HEAD:
				livingEntity.getEquipment().setHelmetDropChance(dropChance);
				break;
			case CHEST:
				livingEntity.getEquipment().setChestplateDropChance(dropChance);
				break;
			case LEGS:
				livingEntity.getEquipment().setLeggingsDropChance(dropChance);
				break;
			case FEET:
				livingEntity.getEquipment().setBootsDropChance(dropChance);
				break;
		}
	}
	
	/**
	 * Sets the {@link LivingEntity} to update.
	 *
	 * @param livingEntity the {@link LivingEntity} to update.
	 */
	public synchronized EquipmentBuilder setLivingEntity(@NotNull LivingEntity livingEntity) {
		this.livingEntity = livingEntity;
		return this;
	}

	/**
	 * @param silent the silent flag.
	 * @return this {@code EquipmentBuilder}.
	 */
	public synchronized EquipmentBuilder setSilent(boolean silent) {
		this.silent = silent;
		return this;
	}

	/**
	 * Sets the item in the specified equipment slot.
	 * @param equipmentSlot the equipment slot.
	 * @param itemStack the item stack.
	 * @return 		this {@code EquipmentBuilder}.
	 */
	public synchronized EquipmentBuilder setItem(EquipmentSlot equipmentSlot, ItemStack itemStack) {
		return setItem(equipmentSlot, new EquipmentItem(itemStack, 0));
	}

	/**
	 * Sets the item in the specified equipment slot.
	 * @param equipmentSlot the equipment slot.
	 * @param itemStack the item stack.
	 * @param dropChance the drop chance.
	 * @return this {@code EquipmentBuilder}.
	 */
	public synchronized EquipmentBuilder setItem(EquipmentSlot equipmentSlot, ItemStack itemStack, float dropChance) {
		return setItem(equipmentSlot, new EquipmentItem(itemStack, dropChance));
	}

	/**
	 * Sets the item in the specified equipment slot.
	 * @param equipmentSlot the equipment slot.
	 * @param equipmentItem the equipment item.
	 * @return this {@code EquipmentBuilder}.
	 */
	public EquipmentBuilder setItem(@NotNull EquipmentSlot equipmentSlot, @NotNull EquipmentItem equipmentItem) {
		equipmentMap.put(Objects.requireNonNull(equipmentSlot), Objects.requireNonNull(equipmentItem));
		return this;
	}
	
}