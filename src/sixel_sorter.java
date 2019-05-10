//import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class sixel_sorter {
    public int[][] image_pixels;
    public int[][] image_original_px;
    public int[] full_sort;
    public int size;
    public int h;
    public int w;

    public enum SORT{
        VERTICAL, HORIZONTAL, DIAGONAL_POS, DIAGONAL_NEG, RADIAL, NONE;
    }
    /**
     * image constructor
     * @param i
     */
    public sixel_sorter(BufferedImage i){
        h = i.getHeight();
        w = i.getWidth();
        size = h * w;
        image_pixels = new int[w][h];
        image_original_px = new int[w][h];
        full_sort = new int[w*h];
        for(int c = 0; c < w; c++){
            for(int r = 0; r < h; r++){
                image_pixels[c][r] = i.getRGB(c, r);
                image_original_px[c][r] = i.getRGB(c, r);
                full_sort[r*h + c] = i.getRGB(c, r);

            }

        }

        Arrays.sort(full_sort);
    }

    /**
     * copy constructor
     */
    public sixel_sorter(int[][] img, int[][] orig, int[] full, int z, int h, int w){
        this.image_pixels = img;
        this.image_original_px = orig;
        this.full_sort = full;
        this.size = z;
        this.h = h;
        this.w = w;

    }

    /**
     * Projection
     * modifies screen
     * inefficient but that's kind of the game here
     */
    public sixel_sorter projection(sixel_sorter lens, sixel_sorter screen){

        int[] lens_list = lens.full_sort.clone();
        int[] spectrum = screen.full_sort.clone();

        int l = lens.size;
        int s = screen.size;
        int li, si;
        int mux = lens.size * screen.size;
        if ( lens.size > screen.size ){
            // this is fine - quasirandom sampling

            for(int i = 0; i < mux; i++){
                li = i/s;
                si = i/l;
                spectrum[si] = lens_list[li];

            }

        } else if ( lens.size == screen.size ){
            // this is ideal
            spectrum = lens_list.clone();

        } else {
            // implicit - lens.size < screen.size
            // works fine for now - may want to look into averaging

            for(int i = 0; i < mux; i++){
                li = i/s;
                si = i/l;
                spectrum[li] = lens_list[si];

            }
        }
        screen = injection(screen, spectrum);
        return screen;

    }

    /**
     *
     */
    public sixel_sorter injection(sixel_sorter target, int[] spectrum){


        return target;

    }

    /**
     *
     *
     */
    public void h_sort(sixel_sorter t){


    }

    /**
     *
     *
     */
    public void v_sort(sixel_sorter t){


    }

    /**
     *
     * @param t
     */
    public void r_sort(sixel_sorter t){

    }

    /**
     *
     * @param t
     */
    public void dp_sort(sixel_sorter t){


    }

    /**
     *
     * @param t
     */
    public void dn_sort(sixel_sorter t){

    }

    /**
     *
     * @param t
     * @param initial
     * @param secondary
     * @param ternary
     * @param terminal
     * @param depth
     * @return
     */
    public sixel_sorter multisort(sixel_sorter t, SORT initial, SORT secondary, SORT ternary, SORT terminal, int depth){
        SORT e[]  = {initial, secondary, ternary, terminal};
        for(int i = 0; i < depth*3; i++){

            switch (e[i%3]){
                case VERTICAL:
                    v_sort(t);
                    break;
                case HORIZONTAL:
                    h_sort(t);
                    break;
                case RADIAL:
                    r_sort(t);
                    break;
                case DIAGONAL_NEG:
                    dn_sort(t);
                    break;
                case DIAGONAL_POS:
                    dp_sort(t);
                    break;
                default:
                    break;
            }
        }
        switch (e[3]){
            case VERTICAL:
                v_sort(t);
                break;
            case HORIZONTAL:
                h_sort(t);
                break;
            case RADIAL:
                r_sort(t);
                break;
            case DIAGONAL_NEG:
                dn_sort(t);
                break;
            case DIAGONAL_POS:
                dp_sort(t);
                break;
            default:
                break;
        }
        return t;


    }

}// end of class
