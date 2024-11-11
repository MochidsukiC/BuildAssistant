package jp.houlab.mochidsuki.buildAssistant.roadbuilder;


import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BuildRoad {
    public static void build(Player player, Material material,int thick) {
        // サンプルデータ

        // 近似式f(x)を生成
        UnivariateFunction f = createApproximationFunction(RoadAxes.get(player));

        // f(x)を二回微分したg(x)を算出
        UnivariateFunction g = createSecondDerivativeFunction(f);

        // g(x)の逆数h(x)を算出
        UnivariateFunction h = createReciprocalFunction(g);

        // 任意の点A(a, f(a))
        double a = 2.5;
        double f_a = f.value(a);

        // 点Aにおける接線I(x)を算出
        UnivariateFunction I = createTangentLine(h, a, f_a);

        // 点B, 点Cのx座標b, cを算出
        double[] bc = findPointsOnTangent(I, a, thick);
        double b = bc[0];
        double c = bc[1];

        System.out.println("近似式 f(x): " + f);
        System.out.println("二階微分 g(x): " + g);
        System.out.println("逆数 h(x): " + h);
        System.out.println("接線 I(x): " + I);
        System.out.println("点A(a, f(a)): (" + a + ", " + f_a + ")");
        System.out.println("点B(b, I(b)): (" + b + ", " + I.value(b) + ")");
        System.out.println("点C(c, I(c)): (" + c + ", " + I.value(c) + ")");

        for(Axes axes : RoadAxes.get(player)){
            double x = axes.getX();
            double y = axes.getY();
            double z = axes.getZ();
            for(double i = b; i < (c-b)*10;i = i+0.1){
                player.getWorld().spawnParticle(Particle.ASH,new Location(player.getWorld(),i,I.value(i),z),10);
            }
        }
    }

    // 近似式f(x)を生成
    public static UnivariateFunction createApproximationFunction(List<Axes> numbers) {
        double[] x = new double[numbers.size()];
        double[] y = new double[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            x[i] = numbers.get(i).getX();
            y[i] = numbers.get(i).getY();
        }
        SplineInterpolator interpolator = new SplineInterpolator();
        return interpolator.interpolate(x, y);
    }

    // f(x)を二回微分したg(x)を算出
    public static UnivariateFunction createSecondDerivativeFunction(UnivariateFunction f) {
        return x -> {
            UnivariateDifferentiableFunction df = (UnivariateDifferentiableFunction) f;
            DerivativeStructure xDS = new DerivativeStructure(1, 2, 0, x);
            return df.value(xDS).getPartialDerivative(2);
        };
    }

    // g(x)の逆数h(x)を算出
    public static UnivariateFunction createReciprocalFunction(UnivariateFunction g) {
        return x -> 1.0 / g.value(x);
    }

    // 点Aにおける接線I(x)を算出
    public static UnivariateFunction createTangentLine(UnivariateFunction h, double a, double f_a) {
        double slope = h.value(a);
        return x -> slope * (x - a) + f_a;
    }

    // 点B, 点Cのx座標b, cを算出
    public static double[] findPointsOnTangent(UnivariateFunction I, double a, double t) {
        UnivariateDifferentiableFunction equation = new UnivariateDifferentiableFunction() {
            @Override
            public double value(double x) {
                double b = a - x;
                double c = a + x;
                return Math.pow(I.value(c) - I.value(b), 2) - Math.pow(t, 2);
            }

            @Override
            public DerivativeStructure value(DerivativeStructure t) {
                double b = a - t.getValue();
                double c = a + t.getValue();
                DerivativeStructure I_c = new DerivativeStructure(1, 1, 0, I.value(c));
                DerivativeStructure I_b = new DerivativeStructure(1, 1, 0, I.value(b));
                return I_c.subtract(I_b).pow(2).subtract(t.pow(2));
            }
        };
        NewtonRaphsonSolver solver = new NewtonRaphsonSolver();
        double x = solver.solve(100, equation, 0, t / 2);
        return new double[] {a - x, a + x};
    }

    public static HashMap<Player, List<Axes>> RoadAxes = new HashMap<>();

}


