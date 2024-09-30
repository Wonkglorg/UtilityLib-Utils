package com.wonkglorg.utilitylib.location;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

@SuppressWarnings("unused")
public final class LocationUtils {
	
	/**
	 * Change a string location to Location object
	 *
	 * @param string as String
	 * @return string as location
	 */
	public static Location changeStringLocationToLocation(String string) {
		return changeStringLocationToLocationEye(string);
	}
	
	/**
	 * Change a string location to Location object
	 *
	 * @param string as string
	 * @return string as locaiton
	 */
	public static Location changeStringLocationToLocationEye(String string) {
		String[] locationArray = string.split(",");
		World w = Bukkit.getServer().getWorld(locationArray[0]);
		float x = Float.parseFloat(locationArray[1]);
		float y = Float.parseFloat(locationArray[2]);
		float z = Float.parseFloat(locationArray[3]);
		if(locationArray.length == 6){
			float yaw = Float.parseFloat(locationArray[4]);
			float pitch = Float.parseFloat(locationArray[5]);
			return new Location(w, x, y, z, yaw, pitch);
		}
		return new Location(w, x, y, z);
	}
	
	/**
	 * @param location
	 * @return location as string
	 */
	public static String changeLocationToString(Location location) {
		return location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
	}
	
	/**
	 * @param location
	 * @return location as String
	 */
	public static String changeLocationToStringEye(Location location) {
		return location.getWorld().getName() +
			   "," +
			   location.getBlockX() +
			   "," +
			   location.getBlockY() +
			   "," +
			   location.getBlockZ() +
			   "," +
			   location.getYaw() +
			   "," +
			   location.getPitch();
	}
	
	/**
	 * @param chunk
	 * @return string as Chunk
	 */
	public static Chunk changeStringChunkToChunk(String chunk) {
		String[] a = chunk.split(",");
		World w = Bukkit.getServer().getWorld(a[0]);
		if(w == null){
			return null;
		}
		return w.getChunkAt(Integer.parseInt(a[1]), Integer.parseInt(a[2]));
	}
	
	/**
	 * @param chunk
	 * @return chunk as string
	 */
	public static String changeChunkToString(Chunk chunk) {
		return chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ();
	}

	
}
