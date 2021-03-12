/*
 * TODO
 * -Calculate score for each player
 * 		-Take a look at "Foreseeable problems" section of Project Plan
 * -Holds array/list of tiles
 * -Make board image by compositing tiles
 */

/*
    85x85
    middle  43
    *NOTE*
    Rotation is handled at Tile itself.
 */

import java.awt.image.BufferedImage;

public class CarcassonneMap {
    CarcassonneTile[][] map;

    public CarcassonneMap() {
        this.map = new CarcassonneTile[85][85];
        // TODO change this, this mess below is simple start tile for test purposes.
        this.map[43][43] = new CarcassonneTile(new Side[] {
                new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //N
                new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //W
                new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //S
                new Side(TerrainType.Farm, TerrainType.River, TerrainType.Farm)  //E
        });
    }

    public boolean canAddAt(CarcassonneTile t, int x, int y) {
        // Check if it's possible to add tile t at x
        // Check according to tile T rotation
        return false;
    }

    public int calcScore() {
        // Calculate score
        return -1;
    }

    public BufferedImage render() {
        // get maximum/minimum x and y value
        // composite
        return null;
    }

    public static void main(String[] args) {

    }
}
