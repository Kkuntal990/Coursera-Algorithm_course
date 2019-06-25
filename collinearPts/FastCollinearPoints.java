/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();

    private HashMap<Double, ArrayList<Point>> foundStartingpoints = new HashMap<>();

    public FastCollinearPoints(Point[] points) {
        checkArgs(points);

        int n = points.length;

        Point[] pointsCopy = Arrays.copyOf(points, n);

        Arrays.sort(pointsCopy);

        for (Point head: points) {
            Arrays.sort(pointsCopy, head.slopeOrder());



            ArrayList<Point> pointsOnLine = new ArrayList<>();

            double prev = Double.NEGATIVE_INFINITY;
            double curr = 0.0;

            for (int j = 1; j < n; j++) {
                curr = head.slopeTo(pointsCopy[j]);

                if (curr == prev) {
                    pointsOnLine.add(pointsCopy[j]);
                }
                else {
                    addSegment(pointsOnLine, head, prev);

                    pointsOnLine.clear();

                    pointsOnLine.add(pointsCopy[j]);
                }

                prev = curr;

            }
            addSegment(pointsOnLine, head, prev);


        }

        lineSegments = lineSegmentArrayList.toArray(new LineSegment[lineSegmentArrayList.size()]);


    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments()
    {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private void checkArgs(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        for (int i = 0 ; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        for (int j = 0 ; j < points.length - 1; j++) {
            for (int k = j +1; k < points.length; k++) {
                if (points[j].compareTo(points[k]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    private void addSegment(ArrayList<Point> pointsOnLine, Point head, double key) {

        if (pointsOnLine.size() < 3) {
            return;
        }

        pointsOnLine.add(head);

        ArrayList<Point> startingPoints = foundStartingpoints.get(key);
        Collections.sort(pointsOnLine);

        Point startPoint = pointsOnLine.get(0);
        Point endPoint = pointsOnLine.get(pointsOnLine.size() - 1);

        if (startingPoints == null) {
            startingPoints = new ArrayList<>();
            startingPoints.add(startPoint);
            foundStartingpoints.put(key, startingPoints);
        }
        else {
            for (Point point: startingPoints) {
                if (point.compareTo(startPoint) == 0) {
                    return;
                }
            }

            startingPoints.add(startPoint);
        }

        lineSegmentArrayList.add(new LineSegment(startPoint, endPoint));

    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
