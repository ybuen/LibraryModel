package library.services;

import java.util.ArrayList;

public class IndexService {

    ArrayList<String> prefixes;
    ArrayList<Integer> prefixCounts;


    public IndexService() {
        this.prefixes = new ArrayList<String>();
        this.prefixCounts = new ArrayList<Integer>();
    }


    public String getNewIndex(String prefix) {
        int index = this.prefixes.indexOf(prefix);

        if (index == -1) {
            this.prefixes.add(prefix);
            this.prefixCounts.add(0);

            index = this.prefixes.indexOf(prefix);
        }

        int prefixCount = this.prefixCounts.get(index);
        prefixCount = prefixCount + 1;

        prefixCounts.set(index, prefixCount);

        return prefix + prefixCount;
    }
}
