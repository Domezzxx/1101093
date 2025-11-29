import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFrame extends JFrame {
    private JLabel lblDateTime;
    private Timer timer;

    public HomeFrame() {
        initComponents();
        startClock();
    }

    private void initComponents() {
        setTitle("ปลายฟ้า LAUNDRY");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Bar Panel
        JPanel topBar = createTopBar();

        // Header Panel with Logo
        JPanel headerPanel = createHeaderPanel();

        // Main Menu Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(230, 235, 245));
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Row 1: ซัก, ซักรีด, ซักแห้ง, เช็คสถานะ
        String[] row1Labels = {"ซัก", "ซักรีด", "ซักแห้ง", "เช็คสถานะ"};
        String[] row1Icons = {"wash.png", "iron.png", "dryclean.png", "status.png"};

        for (int i = 0; i < 4; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            mainPanel.add(createServiceCard(row1Labels[i], row1Icons[i]), gbc);
        }

        // Row 2: รายการ, ประวัติการใช้งาน
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(createServiceCard("รายการ", "list.png"), gbc);

        gbc.gridx = 1;
        mainPanel.add(createServiceCard("ประวัติการใช้งาน", "history.png"), gbc);

        // Combine top bar and header
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(topBar, BorderLayout.NORTH);
        topPanel.add(headerPanel, BorderLayout.CENTER);

        // Add to frame
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(0, 204, 204));
        topBar.setPreferredSize(new Dimension(1100, 50));
        topBar.setLayout(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // Left side - Contact info
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftPanel.setOpaque(false);

        JLabel phoneLabel = new JLabel("📞 01-234-5678");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JLabel lineLabel = new JLabel("💬 @Laundry Clean & Fresh");
        lineLabel.setForeground(Color.WHITE);
        lineLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        leftPanel.add(phoneLabel);
        leftPanel.add(lineLabel);

        // Right side - Time and User
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        lblDateTime = new JLabel();
        lblDateTime.setForeground(Color.WHITE);
        lblDateTime.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDateTime.setIcon(new ImageIcon(createClockIcon()));

        JButton userButton = new JButton("จิรนันต์ เจียงบัติ ▼");
        userButton.setBackground(Color.WHITE);
        userButton.setForeground(new Color(0, 204, 204));
        userButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        userButton.setFocusPainted(false);
        userButton.setBorderPainted(false);
        userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userButton.setPreferredSize(new Dimension(150, 30));

        rightPanel.add(lblDateTime);
        rightPanel.add(userButton);

        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(230, 235, 245));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // Logo
        JLabel logoLabel = new JLabel(createLogoIcon());

        // Title
        JLabel titleLabel = new JLabel("ปลายฟ้า LAUNDRY");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 50));

        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createServiceCard(String title, String iconName) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setLayout(new BorderLayout(0, 10));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        card.setPreferredSize(new Dimension(220, 180));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon
        JLabel iconLabel = new JLabel(createServiceIcon(iconName, title));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(new Color(60, 60, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(240, 245, 255));
                card.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.repaint();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // เมื่อกดปุ่ม ซัก, ซักรีด, หรือซักแห้ง จะเปิดหน้า LaundryServiceFrame
                if (title.equals("ซัก") || title.equals("ซักรีด") || title.equals("ซักแห้ง")) {
                    openLaundryServiceFrame();
                } else {
                    JOptionPane.showMessageDialog(HomeFrame.this,
                            "เปิดหน้า: " + title,
                            "แจ้งเตือน",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        return card;
    }

    private void openLaundryServiceFrame() {
        // ซ่อนหน้า Home
        this.setVisible(false);

        // เปิดหน้า LaundryServiceFrame
        SwingUtilities.invokeLater(() -> {
            LaundryServiceFrame serviceFrame = new LaundryServiceFrame();
            serviceFrame.setVisible(true);

            // เมื่อปิดหน้า LaundryServiceFrame ให้กลับมาแสดงหน้า Home
            serviceFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    // เปลี่ยนจาก windowClosing เป็น windowClosed
                    HomeFrame.this.setVisible(true);
                }
            });
        });
    }

    private ImageIcon createServiceIcon(String iconName, String serviceName) {
        // สร้างไอคอนเป็นรูปภาพตามชื่อบริการ
        int size = 100;
        Image img = new ImageIcon("resources/images/" + iconName).getImage();

        // ถ้าไม่มีรูป ให้สร้างไอคอนสำรอง
        if (img.getWidth(null) <= 0) {
            return createDefaultIcon(serviceName, size);
        }

        return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    private ImageIcon createDefaultIcon(String serviceName, int size) {
        // สร้างไอคอนสำรองตามชื่อบริการ
        Image img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color iconColor;
        String emoji;

        switch (serviceName) {
            case "ซัก":
                iconColor = new Color(100, 180, 255);
                emoji = "🫧";
                break;
            case "ซักรีด":
                iconColor = new Color(255, 160, 120);
                emoji = "👔";
                break;
            case "ซักแห้ง":
                iconColor = new Color(150, 200, 255);
                emoji = "👕";
                break;
            case "เช็คสถานะ":
                iconColor = new Color(120, 200, 255);
                emoji = "✨";
                break;
            case "รายการ":
                iconColor = new Color(100, 170, 255);
                emoji = "🧺";
                break;
            case "ประวัติการใช้งาน":
                iconColor = new Color(150, 180, 255);
                emoji = "📋";
                break;
            default:
                iconColor = new Color(200, 200, 200);
                emoji = "⚙️";
        }

        // วาดวงกลมพื้นหลัง
        g2.setColor(iconColor);
        g2.fillOval(10, 10, size - 20, size - 20);

        // วาด emoji
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(emoji)) / 2;
        int y = ((size - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(emoji, x, y);

        g2.dispose();
        return new ImageIcon(img);
    }

    private ImageIcon createLogoIcon() {
        // สร้างโลโก้วงกลม
        int size = 70;
        Image img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // วงกลมพื้นหลัง
        g2.setColor(new Color(0, 180, 220));
        g2.fillOval(0, 0, size, size);

        // เส้นขอบ
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(2, 2, size - 4, size - 4);

        // วาดเครื่องซักผ้า (รูปง่ายๆ)
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(15, 20, 40, 35, 5, 5);
        g2.setColor(new Color(0, 180, 220));
        g2.fillOval(22, 28, 26, 26);

        g2.dispose();
        return new ImageIcon(img);
    }

    private Image createClockIcon() {
        int size = 16;
        Image img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillOval(0, 0, size, size);
        g2.setColor(new Color(0, 204, 204));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(8, 8, 8, 4);
        g2.drawLine(8, 8, 11, 8);

        g2.dispose();
        return img;
    }

    private void startClock() {
        // แสดงข้อความคงที่สำหรับเวลาทำการ
        lblDateTime.setText("Mon - Sat 6:00 - 16:00");

        // ถ้าต้องการแสดงเวลาปัจจุบัน ใช้โค้ดนี้แทน:
        /*
        timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            lblDateTime.setText(sdf.format(new Date()));
        });
        timer.start();
        */
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            HomeFrame frame = new HomeFrame();
            frame.setVisible(true);
        });
    }
}