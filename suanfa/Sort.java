import java.util.Arrays;

/**
 * @Date: 2022/8/29 - 08 - 29 - 22:15
 * @Description: java
 * @version: 1.0
 */
public class Sort {

    public static void main(String[] args) {
        int[] nums = new int[]{1,10,8,5,9,6,4,3,7};
        stackASC(nums);
        for (int i : nums) {
            System.out.print("  "+i);
        }


    }

    //冒泡排序--ASC
    public static int[] asc(int[] nums){
        for(int i=0;i<nums.length-1;i++){
            boolean swap =false;//设置标记，false代表未交换。
            for(int j=nums.length-1;j>i;j--){
                int temp=nums[j];
                if(nums[j]<nums[j-1]){
                    nums[j]=nums[j-1];
                    nums[j-1]=temp;
                    swap = true;
                }
            }
            if(!swap) break;//如果本轮未发生交换就直接输出结果
        }
        return nums;
    }
    public static int[] asc1(int[] nums){
        for(int i =0;i< nums.length-1;i++){
            for(int j=0;j< nums.length-1-i ; j++){
                int temp = nums[j];
                if(nums[j]>nums[j+1]){
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
        return nums;
    }
    public static int[] ascOver(int[] nums){
        int last = nums.length-1;
        while(true){
            int lastTemp = 0;
            for(int j=0;j< last ; j++){
                int temp = nums[j];
                if(nums[j]>nums[j+1]){
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                    lastTemp = j;
                }
            }
            last = lastTemp;
            if(last == 0) break;
        }
        return nums;
    }


    //冒泡排序--DESC
    public static int[] desc(int[] nums){
        for(int i=0;i< nums.length-1;i++){
            for(int j=nums.length-1;j>i;j--){
                int temp = nums[j];
                if(nums[j]>nums[j-1]){
                    nums[j]=nums[j-1];
                    nums[j-1]=temp;
                }
            }
        }
        return nums;
    }
    public static int[] desc1(int[] nums){
        for(int i =0;i< nums.length-1 ; i++){
            for(int j=0; j<nums.length-1-i ;j++){
                int temp = nums[j];
                if(nums[j]<nums[j+1]){
                    nums[j] = nums[j+1];
                    nums[j+1] =temp;
                }
            }
        }
        return nums;
    }

    // 快速排序--单边循环
    public static void quickSortOne(int[] nums,int left,int right){
        if(left>=right){
            return;
        }
        int partition = partitionOne(nums, left, right);
        quickSortOne(nums,left,partition-1);//左侧区间快排
        quickSortOne(nums,partition+1,right);//右侧区间快排
    }
    public static int partitionOne(int[] nums,int left,int right){
        int i,j;
        int temp;
        for(i=left,j=left;j<right;j++){
            temp = nums[i];
            if(nums[j] < nums[right]){
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
            }
        }
        temp = nums[i];
        nums[i] = nums[j];//最后将元素移动到最终位置
        nums[j] = temp;
        return i;//将元素的位置返回
    }

    //快速排序--双边循环
    public static void quickSort0(int[] nums,int left,int right){
        if(left<right){
            int centrum = partition1(nums,left,right);
            quickSort0(nums,0,centrum-1);//左划分排序
            quickSort0(nums,centrum+1,right);//右划分排序
        }
    }
    public static int partition1(int[] nums,int left,int right){
        int temp=nums[left];
        while(left<right){
            while(left<right&&nums[right]>=temp) right--;
            nums[left] = nums[right];
            while(left<right&&nums[left]<temp) left++;
            nums[right] = nums[left];
        }
        nums[left]=temp;
        return left;
    }

    public static void quickSort1(int[] nums,int left,int right){
        if(left<right){
            int centrum = partition(nums,0, nums.length-1);//第一次中枢归位，返回中枢位置。
            // int lCentrum = partition(nums, 0, centrum - 1);//中枢左侧无序数组中枢归位，返回左侧数组中枢位置
            quickSort1(nums,0,centrum-1);//左侧无序数组中枢归位
            // int RCentrum = partition(nums,centrum+1, nums.length-1);//中枢右侧无序数组中枢归位，返回右侧数组中枢位置
            quickSort1(nums,centrum+1, nums.length-1);//右侧无序数组中枢归位
        }
    }
    public static int partition(int[] nums,int left,int right){
        // int left=0,right=nums.length-1;
        int temp=nums[left];
        while(left<right){//等于时进入判断无任何意义
            while(left<right&&nums[right]>=temp) right--;
            nums[left]=nums[right];
            while(left<right&&nums[left]<=temp) left++;
            nums[right] = nums[left];
        }
        nums[left] =temp;
        return left;
    }



    //直接插入排序--temp做哨兵--升序
    public static int[] insertAsc0(int[] nums){
        int i,j,temp;
        for(i=0;i<nums.length-1;i++){
            if(nums[i+1]<nums[i]){
                temp=nums[i+1];
                for(j=i;j>0&&nums[j]>temp;j--){//核心
                    nums[j+1]=nums[j];
                }
                nums[j] =temp;
            }
        }
        return nums;
    }
    public static int[] insertAsc1(int[] nums) {
        int i, j, temp;
        for (i = 1; i <= nums.length - 1; i++) {
            if (nums[i] < nums[i - 1]) {
                temp = nums[i];
                for (j = i; j > 0 && temp < nums[j - 1]; j--) {
                    nums[j] = nums[j - 1];
                }
                nums[j] = temp;
            }
        }
        return nums;
    }
    public static int[] insertAsc00(int[] nums) {
        for(int i=0;i< nums.length-1;i++){
            if(nums[i]>nums[i+1]) {
                int temp = nums[i+1],j;
                for (j = i; j > 0 &&nums[j] > temp; j--) {
                    nums[j+1] = nums[j];
                }
                nums[j+1] = temp;
                System.out.println(Arrays.toString(nums));
            }
        }
        return nums;
    }
    public static int[] insertAsc01(int[] nums) {
        for(int i=1;i< nums.length;i++){
            if(nums[i-1] > nums[i]) {
                int temp = nums[i],j;
                for (j = i; j > 0 &&nums[j-1] > temp; j--) {
                    nums[j] = nums[j-1];
                }
                nums[j] = temp;
                System.out.println(Arrays.toString(nums));
            }
        }
        return nums;
    }

    //直接插入排序--temp做哨兵--降序
    public static int[] insertDesc0(int[] nums){
        int i,j,temp;
        for(i=0;i<nums.length-1;i++){
            if(nums[i+1]>nums[i]){
                temp=nums[i+1];
                for(j=i;j>=0&&nums[j]<temp;j--){
                    nums[j+1]=nums[j];
                }
                nums[j+1]=temp;
            }
        }
        return nums;
    }
    public static int[] insertDesc1(int[] nums){
        int i,j,temp;
        for(i=1;i<=nums.length-1;i++){
            if(nums[i]>nums[i-1]){
                temp = nums[i];
                for(j=i;j>0&&temp>nums[j-1];j--){
                    nums[j] = nums[j-1];
                }
                nums[j]=temp;
            }
        }
        return nums;
    }

    //折半插入排序--ASC
    public static int[] halfInsertAsc0(int[] nums){
        for(int i=0;i<nums.length-1;i++){
            if(nums[i+1]<nums[i]){
                int temp = nums[i+1];
                int left=0,right=i-1,mid=0;
                while(left<=right){
                    mid = left+(right-left)/2;
                    if(temp>nums[mid]){
                        left=mid+1;
                    }else right=mid-1;
                }
                for(int j=i+1;j>left;j--){
                    nums[j]=nums[j-1];
                }
                nums[left] = temp;
            }
        }
        return nums;
    }
    public static int[] halfInsertAsc1(int[] nums){
        for(int i=1;i<=nums.length-1;i++){
            if(nums[i]<nums[i-1]){
                int temp = nums[i],left=0,right=i-1;
                while(left<=right){
                    int mid= left+(right-left)/2;
                    if(temp>nums[mid]){
                        left=mid+1;
                    }else right=mid-1;
                }
                for(int j=i;j>left;j--){
                    nums[j]=nums[j-1];
                }
                nums[left]=temp;
            }
        }
        return nums;
    }

    //折半插入排序--DESC
    public static int[] halfInsertDesc0(int[] nums){
        for(int i=0;i<nums.length-1;i++){
            if(nums[i+1]>nums[i]){
                int temp = nums[i+1];
                int left=0,right=i;
                while(left<=right){
                    int mid = left+(right-left)/2;
                    if(temp>nums[mid]){
                        right=mid-1;
                    }else{
                        left=mid+1;
                    }
                }
                for(int j=i+1;j>left;j--){
                    nums[j] = nums[j-1];
                }
                nums[left] =temp;
            }
        }
        return nums;
    }
    public static int[] halfInsertDesc1(int[] nums){
        for(int i=1;i<=nums.length-1;i++){
            if(nums[i]>nums[i-1]){
                int temp=nums[i],left=0,right=i-1;
                while(left<=right){
                    int mid = left+(right-left)/2;
                    if(temp>nums[mid]){
                        right=mid-1;
                    }else left=mid+1;
                }
                for(int j=i;j>left;j--){
                    nums[j]=nums[j-1];
                }
                nums[left]=temp;
            }
        }
        return nums;
    }

    //希尔排序--升序
    public static int[] shellAsc0(int[] nums){
        for(int d= (nums.length-1)/2;d>0;d=d/2){
            for(int i=0; i <= nums.length-1 ;i++){
                if((i+d)>nums.length-1) break;//解决数组访问越界异常
                if(nums[i]>nums[i+d]){
                    int temp=nums[i+d],j;
                    for(j=i; j>=0 && nums[j] >temp ; j-=d){
                        nums[j+d] = nums[j];
                    }
                    nums[j+d] = temp;
                }
            }
        }
        return nums;
    }
    public static int[] shellAsc1(int[] nums){
       for(int d= nums.length/2; d > 0 ; d =d/2){
           for(int i=d ;i < nums.length; i++){
               if(nums[i-d] > nums[i]){
                   int temp = nums[i],j;
                   for(j = i-d; j>=0 && nums[j] > temp;j-=d){
                       nums[j+d] = nums[j];
                   }
                   nums[j+d] = temp;
               }
               System.out.println(Arrays.toString(nums));
           }
       }
       return nums;
    }

    //希尔排序--降序
    public static int[] shellDesc0(int[] nums){
        for(int d=(nums.length-1)/2;d>0;d = d /2){
            for(int i=0;i< nums.length;i++){
                if((i+d)> nums.length-1) break;//解决数组上界越界
                if(nums[i]<nums[i+d]){
                    int temp = nums[i+d],j;
                    for(j=i ; j>=0 && nums[j] < temp;j-=d){
                        nums[j+d] = nums[j];
                    }
                    nums[j+d] = temp;
                }
            }
        }
        return nums;
    }
    public static int[] shellDesc1(int[] nums){
        for(int d= (nums.length-1)/2;d>0;d = d >>> 1){
            for(int i=d;i< nums.length;i++){
                if(nums[i-d]<nums[i]){
                    int temp=nums[i],j;
                    for(j=i-d; j>=0 && nums[j] < temp;j-=d){
                        nums[j+d] = nums[j];
                    }
                    nums[j+d] = temp;
                }
            }
        }
        return nums;
    }

    //简单选择排序--ASC
    public static int[] chooseAsc(int[] nums){
        for(int i=0;i< nums.length-1;i++){
            int temp=nums[i],min=i;
            for(int j=i;j<=nums.length-1;j++){
                if(nums[j]<nums[min]){
                    min = j;
                }
            }
            if(min!=i){
                nums[i]=nums[min];
                nums[min]=temp;
            }
        }
        return nums;
    }
    //简单选择排序--DESC
    public static int[] chooseDesc(int[] nums){
        for(int i=0;i< nums.length-1;i++){
            int max=i;
            for(int j=i;j<= nums.length-1;j++){
                if(nums[j]>nums[max]){
                    max=j;
                }
            }
            if(max!=i){
                int temp=nums[i];
                nums[i]=nums[max];
                nums[max]=temp;
            }
        }
        return nums;
    }


    // 大根堆的建立--元素下坠后,以下坠元素为根的子树不处理
    public static void bigStack(int[] nums,int left,int right){
        int len = right-left+1;
        for(int i =len/2 ; i > 0 ; i--){
            int temp = nums[i-1];
            int max =i-1;
            if((2*i+1-1)<=len-1){//有右节点,比较左右结点
                if(nums[2*i+1-1]>nums[2*i-1]) max = 2*i+1-1;
                else max=2*i-1;//选出左右节点最大的结点.
                if(nums[max]>nums[i-1]) {//将最大的放置到父节点位置
                    nums[i-1] = nums[max];
                    nums[max] = temp;
                }
            }else{//没有右孩子比较左孩子与父节点
                if(nums[2*i-1]>nums[i-1]){//如果左孩子大于父节点,两者交换
                    nums[i-1] = nums[2*i-1];
                    nums[2*i-1] = temp;
                }
            }
        }
    }

    // 大根堆的建立--元素下坠后,以下坠元素为根的子树处理
    public static void  bigStack0(int[] nums,int left,int right){
        int len = right-left+1;
        for(int i=len/2;i>0;i--){//从非叶子节点的最后一个开始处理
            int root = i-1;//记录根节点
            int temp=nums[root];//保存根节点，并作下沉时的根节点
            for(int j=2*i;j<= len;j *= 2 ){//使用j*2，进入到下坠元素韦根的子树的左右孩子，进行判断
                if(  ((j+1)<=len)  &&  (nums[j+1-1]>nums[j-1])) j++;//当根节点的右孩子存在时，比较左右孩子，找到最大的哪一个将他记录到j中。
                if(nums[j/2-1]>=nums[j-1]) break;//其中的nums[j/2]可以换位temp,两者完全相同由于上一步.已经将左右孩子中较大值记录过了，只需比较根与左右孩子的最大值即可，如果根大，直接退出，如果孩子大交换。
                else{//将根节点与较大节点互换
                    nums[j/2-1] = nums[j-1];
                    nums[j-1] = temp;
                }
            }
        }
    }
    // 堆排序--ASC
    public static void stackASC(int[] nums){
        bigStack0(nums,0,nums.length-1);
        for(int i=nums.length-1 ; i>0 ; i--){
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            bigStack0(nums,0,i-1);
        }
    }

    //堆排序--小根堆的建立
    public static int[] smallStack0(int[] nums,int left,int right){
        int len = right-left+1;
        for(int i = len/2;i>0;i--){
            int temp = nums[i-1];
            for(int j=2*i;j<=len;j *=2){
                if((j+1)<=len&&(nums[j+1-1]<nums[j-1])) j++;
                if(temp<=nums[j-1]) break;
                else{
                    nums[j/2-1] = nums[j-1];
                    nums[j-1] = temp;
                }
            }
        }
        return nums;
    }
    public static int[] smallStack1(int[] nums){
        int len = nums.length;
        for(int i=len/2;i>0;i--){
            int temp=nums[i],j;
            for(j=2*i;j<len;j *= 2){
                if((j+1)<=len&&nums[j+1]<nums[j]) j++;
                if(temp<=nums[j]) break;
                else{
                    nums[j/2] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }
    // 堆排序--DESC
    public static void stackDESC(int[] nums){
        smallStack0(nums,0,nums.length-1);
        for(int i=nums.length-1 ; i>0 ; i--){
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            smallStack0(nums,0,i-1);
        }
    }

    // 堆排序--双指针，一个指根，一个指孩子结点
    public static int[] bigStackDouble(int[] nums){
        int len = nums.length;
        for(int i=len/2;i>0;i++){
            int temp=nums[i],j,p=i;//p指向根
            for(j=2*i;j<len;j*=2){//j指向p的孩子节点
                if((j+1)<=len&&nums[j+1]>nums[j]) j++;
                if(temp>=nums[j]) break;
                else{
                    nums[p] = nums[j];
                    p=j;//将下坠元素作为根
                }
            }
            nums[p]=temp;
        }
        return nums;
    }

}
