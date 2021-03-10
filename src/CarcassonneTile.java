import java.util.Arrays;

/*
 * TODO
 * -Define properties:
 * 		river connection
 * 		road  connection
 * 		castle/farmland handling
 * -Meeples
 * -Connection validations
 * 		ex) can this piece placed here?
 */

/*
 * NW  N  NE
 * W   C   E
 * SW  S  SE
 *
 * More likely
 * EX) 1.png
 * Road: S, E
 * River: null
 * City: null
 * 
 * EX) 28.png
 * Road: null
 * River: null
 * City: N, E, S
 * 
 * EX) 40.png
 * Road: null
 * River: W, S
 * City: N, E
 * 
 * 
 * 
 * Maybe
 * EX) 1.png
 * G  G  G
 * G  R  R
 * G  R  G
 * 
 * EX) 28.png
 * G  C  C
 * G  C  C
 * G  C  C
 * 
 * EX) 40.png
 * C  C  C
 * R  R  C
 * G  R  C
 * 
 * Maybe 
 * EX) 1.png
 * N: F  F  F
 * W: F  F  F
 * S: F  R  F
 * E: F  R  F
 * 
 * EX) 28.png
 * N: C  C  C
 * W: F  F  F
 * S: C  C  C
 * E: C  C  C
 * 
 * EX) 40.png
 * N: C  C  C
 * W: F  R  F
 * S: F  R  F
 * E: C  C  C
 * 
 *    C  C  C
 * F  F  F  F  C
 * R  R  R  F  C
 * F  F  R  F  C
 *    F  R  F
 *    
 * 
 */
enum Orient {
	N, W, S, E
};

enum TerrainType {
	Farm, River, City, Road
};

class Side {
	TerrainType[] sidetypes;

	Side(TerrainType l, TerrainType m, TerrainType r) {
		sidetypes = new TerrainType[3];
		sidetypes[0] = l;
		sidetypes[1] = m;
		sidetypes[2] = r;
	}

	public TerrainType[] getSide() {
		return this.sidetypes;
	}

	public boolean equals(Object obj) {
		return Arrays.equals(this.sidetypes, ((Side) obj).getSide());
	}
}

public class CarcassonneTile {
	int rotation;
	Side[] sides;

	public CarcassonneTile(Side[] sides) {
		this.sides = new Side[4];
		this.sides[Orient.N.ordinal()] = sides[Orient.N.ordinal()];
		this.sides[Orient.W.ordinal()] = sides[Orient.W.ordinal()];
		this.sides[Orient.S.ordinal()] = sides[Orient.S.ordinal()];
		this.sides[Orient.E.ordinal()] = sides[Orient.E.ordinal()];
	}
	
	public Side[] getSides() {
		return this.sides;
	}
	
	public boolean fit(CarcassonneTile t, Orient o) {
		return this.sides[o.ordinal()].equals(t.getSides()[o.ordinal()]);
	}

	public static void main(String[] args) {
		CarcassonneTile t1 = new CarcassonneTile(new Side[] {
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //N
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //W
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //S
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.City)  //E
		});
		
		CarcassonneTile t2 = new CarcassonneTile(new Side[] {
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm),
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm),
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm),
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm)
		});
		System.out.println("match? :" + t1.fit(t2, Orient.E));
	}
}
