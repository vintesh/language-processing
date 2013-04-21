/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Desgin of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.slr.ds;

import java.util.ArrayList;

/**
 *
 * @author Vintesh
 */
public class ProductionRule {

    private int number;
    private String leftSide;
    private String rightSide;
    private static ArrayList<ProductionRule> instance = new ArrayList<>();

    public ProductionRule(int number, String leftSide, String rightSide) {
        this.number = number;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public static ArrayList<ProductionRule> getInstance() {
        return instance;
    }

    public static void addRule(ProductionRule rule) {
        instance.add(rule);
    }

    public String getLeftSide() {
        return leftSide;
    }

    public int getNumber() {
        return number;
    }

    public String getRightSide() {
        return rightSide;
    }

    public static ProductionRule getProductionByNumber(int number) {
        for (ProductionRule productionRule : instance) {
            if (productionRule.getNumber() == number) {
                return productionRule;
            }
        }
        throw new IllegalStateException("NO Production found of Number: " + number);
    }
}
