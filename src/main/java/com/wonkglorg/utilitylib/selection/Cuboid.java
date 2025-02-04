package com.wonkglorg.utilitylib.selection;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Cuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable, Serializable{
	
	/**
	 * This class is a region/cuboid from one location to another. It can be used for blocks protection and things like WorldEdit.
	 */
	private final String worldName;
	private final int x1, y1, z1;
	private final int x2, y2, z2;
	private final int centerX, centerY, centerZ;
	
	/**
	 * Construct a Cuboid given two Location objects which represent any two corners of the Cuboid. Note: The 2 locations must be on the same world.
	 *
	 * @param l1 - One of the corners
	 * @param l2 - The other corner
	 */
	public Cuboid(Location l1, Location l2) {
		if(!l1.getWorld().equals(l2.getWorld())){
			throw new IllegalArgumentException("Locations must be on the same world");
		}
		this.worldName = l1.getWorld().getName();
		this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
		this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
		this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
		this.x2 = Math.max(l1.getBlockX(), l2.getBlockX()) + 1;
		this.y2 = Math.max(l1.getBlockY(), l2.getBlockY()) + 1;
		this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ()) + 1;
		this.centerX = (this.x1 + this.x2) / 2;
		this.centerY = (this.y1 + this.y2) / 2;
		this.centerZ = (this.z1 + this.z2) / 2;
	}
	
	/**
	 * Construct a one-block Cuboid at the given Location of the Cuboid.
	 *
	 * @param l1 location of the Cuboid
	 */
	public Cuboid(Location l1) {
		this(l1, l1);
	}
	
	/**
	 * Copy constructor.
	 *
	 * @param other - The Cuboid to copy
	 */
	public Cuboid(Cuboid other) {
		this(other.worldName, other.x1, other.y1, other.z1, other.x2, other.y2, other.z2);
	}
	
	/**
	 * Construct a Cuboid in the given World and xyz co-ordinates
	 *
	 * @param world - The Cuboid's world
	 * @param x1 - X co-ordinate of corner 1
	 * @param y1 - Y co-ordinate of corner 1
	 * @param z1 - Z co-ordinate of corner 1
	 * @param x2 - X co-ordinate of corner 2
	 * @param y2 - Y co-ordinate of corner 2
	 * @param z2 - Z co-ordinate of corner 2
	 */
	public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
		this.worldName = world.getName();
		this.x1 = Math.min(x1, x2);
		this.x2 = Math.max(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.y2 = Math.max(y1, y2);
		this.z1 = Math.min(z1, z2);
		this.z2 = Math.max(z1, z2);
		this.centerX = (this.x1 + this.x2) / 2;
		this.centerY = (this.y1 + this.y2) / 2;
		this.centerZ = (this.z1 + this.z2) / 2;
	}
	
	/**
	 * Construct a Cuboid in the given world name and xyz co-ordinates.
	 *
	 * @param worldName - The Cuboid's world name
	 * @param x1 - X co-ordinate of corner 1
	 * @param y1 - Y co-ordinate of corner 1
	 * @param z1 - Z co-ordinate of corner 1
	 * @param x2 - X co-ordinate of corner 2
	 * @param y2 - Y co-ordinate of corner 2
	 * @param z2 - Z co-ordinate of corner 2
	 */
	public Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
		this.worldName = worldName;
		this.x1 = Math.min(x1, x2);
		this.x2 = Math.max(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.y2 = Math.max(y1, y2);
		this.z1 = Math.min(z1, z2);
		this.z2 = Math.max(z1, z2);
		this.centerX = (this.x1 + this.x2) / 2;
		this.centerY = (this.y1 + this.y2) / 2;
		this.centerZ = (this.z1 + this.z2) / 2;
	}
	
	/**
	 * Construct a Cuboid using a map with the following keys: worldName, x1, x2, y1, y2, z1, z2
	 *
	 * @param map - The map of keys.
	 */
	public Cuboid(Map<String, Object> map) {
		this.worldName = (String) map.get("worldName");
		this.x1 = (Integer) map.get("x1");
		this.x2 = (Integer) map.get("x2");
		this.y1 = (Integer) map.get("y1");
		this.y2 = (Integer) map.get("y2");
		this.z1 = (Integer) map.get("z1");
		this.z2 = (Integer) map.get("z2");
		this.centerX = (this.x1 + this.x2) / 2;
		this.centerY = (this.y1 + this.y2) / 2;
		this.centerZ = (this.z1 + this.z2) / 2;
	}
	
	@Override
	public @NotNull Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("worldName", this.worldName);
		map.put("x1", this.x1);
		map.put("y1", this.y1);
		map.put("z1", this.z1);
		map.put("x2", this.x2);
		map.put("y2", this.y2);
		map.put("z2", this.z2);
		return map;
	}
	
	/**
	 * Get the blocks in the Cuboid.
	 *
	 * @return The blocks in the Cuboid
	 */
	public List<Block> getBlocks(Material material) {
		Iterator<Block> blockI = this.iterator();
		List<Block> copy = new ArrayList<>();
		while(blockI.hasNext()){
			Block block = blockI.next();
			if(block.getType().equals(material)){
				copy.add(block);
			}
		}
		return copy;
	}
	
	/**
	 * Get the blocks in the Cuboid.
	 *
	 * @return The blocks in the Cuboid
	 */
	public List<Block> getBlocks() {
		Iterator<Block> blockI = this.iterator();
		List<Block> copy = new ArrayList<>();
		while(blockI.hasNext()) copy.add(blockI.next());
		return copy;
	}
	
	/**
	 * Get the centre of the Cuboid.
	 *
	 * @return Location at the centre of the Cuboid
	 */
	public Location getCenter() {
		int x1 = this.getUpperX() + 1;
		int y1 = this.getUpperY() + 1;
		int z1 = this.getUpperZ() + 1;
		return new Location(this.getWorld(),
				this.getLowerX() + (x1 - this.getLowerX()) / 2.0,
				this.getLowerY() + (y1 - this.getLowerY()) / 2.0,
				this.getLowerZ() + (z1 - this.getLowerZ()) / 2.0);
	}
	
	/**
	 * Get the Cuboid's world.
	 *
	 * @return The World object representing this Cuboid's world
	 * @throws IllegalStateException if the world is not loaded
	 */
	public World getWorld() {
		World world = Bukkit.getWorld(this.worldName);
		if(world == null){
			throw new IllegalStateException("World '" + this.worldName + "' is not loaded");
		}
		return world;
	}
	
	/**
	 * Get the size of this Cuboid along the X axis
	 *
	 * @return Size of Cuboid along the X axis
	 */
	public int getSizeX() {
		return (this.x2 - this.x1) + 1;
	}
	
	/**
	 * Get the size of this Cuboid along the Y axis
	 *
	 * @return Size of Cuboid along the Y axis
	 */
	public int getSizeY() {
		return (this.y2 - this.y1) + 1;
	}
	
	/**
	 * Get the size of this Cuboid along the Z axis
	 *
	 * @return Size of Cuboid along the Z axis
	 */
	public int getSizeZ() {
		return (this.z2 - this.z1) + 1;
	}
	
	/**
	 * Get the minimum X co-ordinate of this Cuboid
	 *
	 * @return the minimum X co-ordinate
	 */
	public int getLowerX() {
		return this.x1;
	}
	
	/**
	 * Get the minimum Y co-ordinate of this Cuboid
	 *
	 * @return the minimum Y co-ordinate
	 */
	public int getLowerY() {
		return this.y1;
	}
	
	/**
	 * Get the minimum Z co-ordinate of this Cuboid
	 *
	 * @return the minimum Z co-ordinate
	 */
	public int getLowerZ() {
		return this.z1;
	}
	
	/**
	 * Get the maximum X co-ordinate of this Cuboid
	 *
	 * @return the maximum X co-ordinate
	 */
	public int getUpperX() {
		return this.x2;
	}
	
	/**
	 * Get the maximum Y co-ordinate of this Cuboid
	 *
	 * @return the maximum Y co-ordinate
	 */
	public int getUpperY() {
		return this.y2;
	}
	
	/**
	 * Get the maximum Z co-ordinate of this Cuboid
	 *
	 * @return the maximum Z co-ordinate
	 */
	public int getUpperZ() {
		return this.z2;
	}
	
	/**
	 * Get the Blocks at the eight corners of the Cuboid.
	 *
	 * @return array of Block objects representing the Cuboid corners
	 */
	public Block[] corners() {
		Block[] res = new Block[8];
		World w = this.getWorld();
		res[0] = w.getBlockAt(this.x1, this.y1, this.z1);
		res[1] = w.getBlockAt(this.x1, this.y1, this.z2);
		res[2] = w.getBlockAt(this.x1, this.y2, this.z1);
		res[3] = w.getBlockAt(this.x1, this.y2, this.z2);
		res[4] = w.getBlockAt(this.x2, this.y1, this.z1);
		res[5] = w.getBlockAt(this.x2, this.y1, this.z2);
		res[6] = w.getBlockAt(this.x2, this.y2, this.z1);
		res[7] = w.getBlockAt(this.x2, this.y2, this.z2);
		return res;
	}
	
	/**
	 * Get the Location of the lower northeast corner of the Cuboid (minimum XYZ co-ordinates).
	 *
	 * @return Location of the lower northeast corner
	 */
	public Location getLowerNE() {
		return new Location(this.getWorld(), this.x1, this.y1, this.z1);
	}
	
	/**
	 * Get the Location of the upper southwest corner of the Cuboid (maximum XYZ co-ordinates).
	 *
	 * @return Location of the upper southwest corner
	 */
	public Location getUpperSW() {
		return new Location(this.getWorld(), this.x2, this.y2, this.z2);
	}
	
	/**
	 * Gets lower South West corner of the selection
	 *
	 * @return a new Location
	 */
	public Location getLowerSW() {
		return new Location(this.getWorld(), this.x2, this.y1, this.z2);
	}
	
	/**
	 * Gets lower South East corner of the selection
	 *
	 * @return a new Location
	 */
	public Location getLowerSE() {
		return new Location(this.getWorld(), this.x1, this.y1, this.z2);
	}
	
	/**
	 * Gets upper South East corner of the selection
	 *
	 * @return a new Location
	 */
	public Location getUpperSE() {
		return new Location(this.getWorld(), this.x1, this.y2, this.z2);
	}
	
	/**
	 * Gets lower North West corner of the selection
	 *
	 * @return a new Location
	 */
	public Location getLowerNW() {
		return new Location(this.getWorld(), this.x2, this.y1, this.z1);
	}
	
	/**
	 * Gets upper North West corner of the selection
	 *
	 * @return a new Location
	 */
	public Location getUpperNW() {
		return new Location(this.getWorld(), this.x2, this.y2, this.z1);
	}
	
	/**
	 * Gets upper North East corner of the selection
	 *
	 * @return a new Location
	 */
	public Location getUpperNE() {
		return new Location(this.getWorld(), this.x1, this.y2, this.z1);
	}
	
	/**
	 * Expand the Cuboid in the given direction by the given amount. Negative amounts will shrink the Cuboid in the given direction. Shrinking a
	 * cuboid's face past the opposite face is not an error and will return a valid Cuboid.
	 *
	 * @param dir - The direction in which to expand
	 * @param amount - The number of blocks by which to expand
	 * @return A new Cuboid expanded by the given direction and amount
	 */
	public Cuboid expand(CuboidDirection dir, int amount) {
		return switch(dir) {
			case North -> new Cuboid(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2);
			case South -> new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2);
			case East -> new Cuboid(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2);
			case West -> new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount);
			case Down -> new Cuboid(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2);
			case Up -> new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2);
			default -> throw new IllegalArgumentException("Invalid direction " + dir);
		};
	}
	
	/**
	 * Shift the Cuboid in the given direction by the given amount.
	 *
	 * @param dir - The direction in which to shift
	 * @param amount - The number of blocks by which to shift
	 * @return A new Cuboid shifted by the given direction and amount
	 */
	public Cuboid shift(CuboidDirection dir, int amount) {
		return expand(dir, amount).expand(dir.opposite(), -amount);
	}
	
	/**
	 * Outset (grow) the Cuboid in the given direction by the given amount.
	 *
	 * @param dir - The direction in which to outset (must be Horizontal, Vertical, or Both)
	 * @param amount - The number of blocks by which to outset
	 * @return A new Cuboid outset by the given direction and amount
	 */
	public Cuboid outset(CuboidDirection dir, int amount) {
		return switch(dir) {
			case Horizontal -> expand(CuboidDirection.North, amount).expand(CuboidDirection.South, amount)
																	.expand(CuboidDirection.East, amount)
																	.expand(CuboidDirection.West, amount);
			case Vertical -> expand(CuboidDirection.Down, amount).expand(CuboidDirection.Up, amount);
			case Both -> outset(CuboidDirection.Horizontal, amount).outset(CuboidDirection.Vertical, amount);
			default -> throw new IllegalArgumentException("Invalid direction " + dir);
		};
	}
	
	/**
	 * Inset (shrink) the Cuboid in the given direction by the given amount. Equivalent to calling outset() with a negative amount.
	 *
	 * @param dir - The direction in which to inset (must be Horizontal, Vertical, or Both)
	 * @param amount - The number of blocks by which to inset
	 * @return A new Cuboid inset by the given direction and amount
	 */
	public Cuboid inset(CuboidDirection dir, int amount) {
		return this.outset(dir, -amount);
	}
	
	/**
	 * Return true if the point at (x,y,z) is contained within this Cuboid.
	 *
	 * @param x - The X co-ordinate
	 * @param y - The Y co-ordinate
	 * @param z - The Z co-ordinate
	 * @return true if the given point is within this Cuboid, false otherwise
	 */
	public boolean contains(int x, int y, int z) {
		return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
	}
	
	/**
	 * Check if the given Block is contained within this Cuboid.
	 *
	 * @param b - The Block to check for
	 * @return true if the Block is within this Cuboid, false otherwise
	 */
	public boolean contains(Block b) {
		return this.contains(b.getLocation());
	}
	
	/**
	 * Check if the given Location is contained within this Cuboid.
	 *
	 * @param location - The Location to check for
	 * @return true if the Location is within this Cuboid, false otherwise
	 */
	public boolean contains(Location location) {
		if(!this.worldName.equals(location.getWorld().getName())){
			return false;
		}
		return this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	/**
	 * Get the volume of this Cuboid.
	 *
	 * @return The Cuboid volume, in blocks
	 */
	public int getVolume() {
		return this.getSizeX() * this.getSizeY() * this.getSizeZ();
	}
	
	/**
	 * Get the average light level of all empty (air) blocks in the Cuboid. Returns 0 if there are no empty blocks.
	 *
	 * @return The average light level of this Cuboid
	 */
	public byte getAverageLightLevel() {
		long total = 0;
		int n = 0;
		for(Block b : this){
			if(b.isEmpty()){
				total += b.getLightLevel();
				++n;
			}
		}
		return n > 0 ? (byte) (total / n) : 0;
	}
	
	/**
	 * Contract the Cuboid, returning a Cuboid with any air around the edges removed, just large enough to include all non-air blocks.
	 *
	 * @return A new Cuboid with no external air blocks
	 */
	public Cuboid contract() {
		return this.contract(CuboidDirection.Down)
				   .contract(CuboidDirection.South)
				   .contract(CuboidDirection.East)
				   .contract(CuboidDirection.Up)
				   .contract(CuboidDirection.North)
				   .contract(CuboidDirection.West);
	}
	
	/**
	 * Contract the Cuboid in the given direction, returning a new Cuboid which has no exterior empty space. E.g. A direction of Down will push the
	 * top face downwards as much as possible.
	 *
	 * @param dir - The direction in which to contract
	 * @return A new Cuboid contracted in the given direction
	 */
	public Cuboid contract(CuboidDirection dir) {
		Cuboid face = getFace(dir.opposite());
		switch(dir) {
			case Down -> {
				while(face.containsOnly(0) && face.getLowerY() > this.getLowerY()){
					face = face.shift(CuboidDirection.Down, 1);
				}
				return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, face.getUpperY(), this.z2);
			}
			case Up -> {
				while(face.containsOnly(0) && face.getUpperY() < this.getUpperY()){
					face = face.shift(CuboidDirection.Up, 1);
				}
				return new Cuboid(this.worldName, this.x1, face.getLowerY(), this.z1, this.x2, this.y2, this.z2);
			}
			case North -> {
				while(face.containsOnly(0) && face.getLowerX() > this.getLowerX()){
					face = face.shift(CuboidDirection.North, 1);
				}
				return new Cuboid(this.worldName, this.x1, this.y1, this.z1, face.getUpperX(), this.y2, this.z2);
			}
			case South -> {
				while(face.containsOnly(0) && face.getUpperX() < this.getUpperX()){
					face = face.shift(CuboidDirection.South, 1);
				}
				return new Cuboid(this.worldName, face.getLowerX(), this.y1, this.z1, this.x2, this.y2, this.z2);
			}
			case East -> {
				while(face.containsOnly(0) && face.getLowerZ() > this.getLowerZ()){
					face = face.shift(CuboidDirection.East, 1);
				}
				return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, face.getUpperZ());
			}
			case West -> {
				while(face.containsOnly(0) && face.getUpperZ() < this.getUpperZ()){
					face = face.shift(CuboidDirection.West, 1);
				}
				return new Cuboid(this.worldName, this.x1, this.y1, face.getLowerZ(), this.x2, this.y2, this.z2);
			}
			default -> throw new IllegalArgumentException("Invalid direction " + dir);
		}
	}
	
	/**
	 * Get the Cuboid representing the face of this Cuboid. The resulting Cuboid will be one block thick in the axis perpendicular to the requested
	 * face.
	 *
	 * @param dir - which face of the Cuboid to get
	 * @return The Cuboid representing this Cuboid's requested face
	 */
	public Cuboid getFace(CuboidDirection dir) {
		return switch(dir) {
			case Down -> new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2);
			case Up -> new Cuboid(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2);
			case North -> new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2);
			case South -> new Cuboid(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2);
			case East -> new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1);
			case West -> new Cuboid(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2);
			default -> throw new IllegalArgumentException("Invalid direction " + dir);
		};
	}
	
	/**
	 * Check if the Cuboid contains only blocks of the given type
	 *
	 * @param blockId - The block ID to check for
	 * @return true if this Cuboid contains only blocks of the given type
	 */
	@SuppressWarnings("deprecation")
	public boolean containsOnly(int blockId) {
		for(Block b : this){
			if(b.getType().getId() != blockId){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Calculates the squared distance between the given Location and the nearest face of the Cuboid.
	 *
	 * @param point - The Location to check
	 * @return The squared distance to the nearest face of the Cuboid or null if the location is in a different world or otherwise invalid
	 */
	public Double getShortestSquaredDistanceToSurface(Location point) {
		if(point.getWorld() != this.getWorld()){
			return null;
		}
		
		double px = point.getX(), py = point.getY(), pz = point.getZ();
		
		double dx = (px < x1) ? (x1 - px) : (px > x2) ? (px - x2) : 0;
		double dy = (py < y1) ? (y1 - py) : (py > y2) ? (py - y2) : 0;
		double dz = (pz < z1) ? (z1 - pz) : (pz > z2) ? (pz - z2) : 0;
		
		if(dx == 0 && dy == 0 && dz == 0){
			return 0.0;
		}
		
		return dx * dx + dy * dy + dz * dz;
	}
	
	/**
	 * Check if the given Location is within range of this Cuboid.
	 *
	 * @param point - The Location to check
	 * @param range - The maximum distance to the Cuboid
	 * @return true if the Location is within range of the Cuboid
	 */
	public boolean isPointInRange(Location point, double range) {
		Double squaredDistance = getShortestSquaredDistanceToSurface(point);
		return squaredDistance != null && squaredDistance <= range * range;
	}
	
	/**
	 * Calculates the actual Euclidean distance using the squared distance method.
	 *
	 * @param point - The Location to check
	 * @return The true distance to the nearest face of the Cuboid or null if the location is invalid
	 */
	public Double getShortestDistanceToSurface(Location point) {
		Double squaredDistance = getShortestSquaredDistanceToSurface(point);
		return (squaredDistance != null) ? Math.sqrt(squaredDistance) : null;
	}
	
	/**
	 * Get the Cuboid big enough to hold both this Cuboid and the given one.
	 *
	 * @param other - The other cuboid.
	 * @return A new Cuboid large enough to hold this Cuboid and the given Cuboid
	 */
	public Cuboid getBoundingCuboid(Cuboid other) {
		if(other == null){
			return this;
		}
		
		int xMin = Math.min(this.getLowerX(), other.getLowerX());
		int yMin = Math.min(this.getLowerY(), other.getLowerY());
		int zMin = Math.min(this.getLowerZ(), other.getLowerZ());
		int xMax = Math.max(this.getUpperX(), other.getUpperX());
		int yMax = Math.max(this.getUpperY(), other.getUpperY());
		int zMax = Math.max(this.getUpperZ(), other.getUpperZ());
		
		return new Cuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
	}
	
	/**
	 * Get a block relative to the lower NE point of the Cuboid.
	 *
	 * @param x - The X co-ordinate
	 * @param y - The Y co-ordinate
	 * @param z - The Z co-ordinate
	 * @return The block at the given position
	 */
	public Block getRelativeBlock(int x, int y, int z) {
		return this.getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
	}
	
	/**
	 * Get a block relative to the lower NE point of the Cuboid in the given World. This version of getRelativeBlock() should be used if being called
	 * many times, to avoid excessive calls to getWorld().
	 *
	 * @param w - The world
	 * @param x - The X co-ordinate
	 * @param y - The Y co-ordinate
	 * @param z - The Z co-ordinate
	 * @return The block at the given position
	 */
	public Block getRelativeBlock(World w, int x, int y, int z) {
		return w.getBlockAt(this.x1 + x, y1 + y, this.z1 + z);
	}
	
	/**
	 * Get a list of the chunks which are fully or partially contained in this cuboid.
	 *
	 * @return A list of Chunk objects
	 */
	public List<Chunk> getChunks() {
		List<Chunk> res = new ArrayList<>();
		
		World w = this.getWorld();
		int x1 = this.getLowerX() & ~0xf;
		int x2 = this.getUpperX() & ~0xf;
		int z1 = this.getLowerZ() & ~0xf;
		int z2 = this.getUpperZ() & ~0xf;
		for(int x = x1; x <= x2; x += 16){
			for(int z = z1; z <= z2; z += 16){
				res.add(w.getChunkAt(x >> 4, z >> 4));
			}
		}
		return res;
	}
	
	public @NotNull Iterator<Block> iterator() {
		return new CuboidIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
	}
	
	@Override
	public Cuboid clone() {
		Cuboid clone;
		try{
			clone = (Cuboid) super.clone();
		} catch(CloneNotSupportedException e){
			clone = new Cuboid(this);
		}
		return clone;
	}
	
	@Override
	public String toString() {
		return "Cuboid: " + this.worldName + "," + this.x1 + "," + this.y1 + "," + this.z1 + "=>" + this.x2 + "," + this.y2 + "," + this.z2;
	}
	
	public static class CuboidIterator implements Iterator<Block>{
		private final World w;
		private final int baseX;
		private final int baseY;
		private final int baseZ;
		private int x, y, z;
		private final int sizeX;
		private final int sizeY;
		private final int sizeZ;
		
		public CuboidIterator(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
			this.w = w;
			this.baseX = x1;
			this.baseY = y1;
			this.baseZ = z1;
			this.sizeX = Math.abs(x2 - x1) + 1;
			this.sizeY = Math.abs(y2 - y1) + 1;
			this.sizeZ = Math.abs(z2 - z1) + 1;
			this.x = this.y = this.z = 0;
		}
		
		public boolean hasNext() {
			return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
		}
		
		public Block next() {
			Block b = this.w.getBlockAt(this.baseX + this.x, this.baseY + this.y, this.baseZ + this.z);
			if(++x >= this.sizeX){
				this.x = 0;
				if(++this.y >= this.sizeY){
					this.y = 0;
					++this.z;
				}
			}
			return b;
		}
		
		public void remove() {
		}
	}
	
	/**
	 * Outlines the selected area with particles.
	 *
	 * @param particle type of particle to spawn
	 */
	public void outline(Particle particle) {
		World world = getWorld();
		int lowerX = getLowerX();
		int upperX = getUpperX();
		int lowerY = getLowerY();
		int upperY = getUpperY();
		int lowerZ = getLowerZ();
		int upperZ = getUpperZ();
		
		for(int x = lowerX; x <= upperX; x++){
			world.spawnParticle(particle, x, lowerY, lowerZ, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, x, lowerY, upperZ, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, x, upperY, lowerZ, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, x, upperY, upperZ, 0, 0, 0, 0, 0, null, true);
		}
		
		for(int y = lowerY; y <= upperY; y++){
			world.spawnParticle(particle, lowerX, y, lowerZ, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, lowerX, y, upperZ, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, upperX, y, lowerZ, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, upperX, y, upperZ, 0, 0, 0, 0, 0, null, true);
		}
		
		for(int z = lowerZ; z <= upperZ; z++){
			world.spawnParticle(particle, lowerX, lowerY, z, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, upperX, lowerY, z, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, lowerX, upperY, z, 0, 0, 0, 0, 0, null, true);
			world.spawnParticle(particle, upperX, upperY, z, 0, 0, 0, 0, 0, null, true);
		}
	}
	
	private void drawLine(Location location1, Location location2, Particle particle) {
		Vector vector = location2.toVector().subtract(location1.toVector());
		if(Objects.equals(vector, new Vector(0, 0, 0))){
			return;
		}
		BlockIterator blockIterator = new BlockIterator(location1.setDirection(vector), 0, (int) location1.distance(location2));
		while(blockIterator.hasNext()){
			Location location = blockIterator.next().getLocation();
			if(!location.getChunk().isLoaded()){
				continue;
			}
			location1.getWorld().spawnParticle(particle, location, 0, 0, 0, 0, 0, null, true);
		}
	}
	
	public enum CuboidDirection{
		North,
		East,
		South,
		West,
		Up,
		Down,
		Horizontal,
		Vertical,
		Both,
		Unknown;
		
		public CuboidDirection opposite() {
			return switch(this) {
				case North -> South;
				case East -> West;
				case South -> North;
				case West -> East;
				case Horizontal -> Vertical;
				case Vertical -> Horizontal;
				case Up -> Down;
				case Down -> Up;
				case Both -> Both;
				default -> Unknown;
			};
		}
		
	}
	
	public int getCenterX() {
		return centerX;
	}
	
	public int getCenterY() {
		return centerY;
	}
	
	public int getCenterZ() {
		return centerZ;
	}
}