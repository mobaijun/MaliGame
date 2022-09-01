import com.mobaijun.ui.GameFrame;
import com.mobaijun.util.KeyListener;

/**
 * 超级玛丽启动类。
 */
public class RunApplication {
    /**
     * 主函数，程序入口
     */
    public static void main(String[] args) throws Exception {
        GameFrame gf = new GameFrame();
        // 创建监听器对象
        KeyListener kl = new KeyListener(gf);
        // 给窗体添加键盘监听器
        gf.addKeyListener(kl);
    }
}
