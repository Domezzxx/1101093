import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class LaundryServiceFrame extends JFrame {
    private JPanel summaryPanel;
    private Map<String, OrderItem> orderItems = new HashMap<>();
    private JLabel totalLabel;
    private int totalAmount = 0;

    // คลาสเก็บข้อมูลรายการสั่งซัก
    class OrderItem {
        String itemName;
        String serviceType;
        int quantity;
        int pricePerItem;

        OrderItem(String itemName, String serviceType, int quantity, int pricePerItem) {
            this.itemName = itemName;
            this.serviceType = serviceType;
            this.quantity = quantity;
            this.pricePerItem = pricePerItem;
        }

        int getTotalPrice() {
            return quantity * pricePerItem;
        }

        String getKey() {
            return itemName + "_" + serviceType;
        }
    }

    public LaundryServiceFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("รายการซักผ้า");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // เปลี่ยนจาก EXIT_ON_CLOSE เป็น DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Bar
        JPanel topBar = createTopBar();

        // Main Content
        JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Left Panel - รายการซักผ้า
        JPanel leftPanel = createLeftPanel();

        // Right Panel - สรุปรายการ
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(0, 204, 204));
        topBar.setPreferredSize(new Dimension(1200, 50));
        topBar.setLayout(new BorderLayout());
        topBar.setBorder(new EmptyBorder(8, 20, 8, 20));

        // Left side - Back button and contact info
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);

        // Back button
        JButton backButton = new JButton("← กลับ");
        backButton.setBackground(new Color(0, 180, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(90, 30));
        backButton.addActionListener(e -> {
            this.dispose();
        });

        JLabel phoneLabel = new JLabel("📞 01-234-5678");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JLabel lineLabel = new JLabel("💬 @Laundry Clean & Fresh");
        lineLabel.setForeground(Color.WHITE);
        lineLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        leftPanel.add(backButton);
        leftPanel.add(phoneLabel);
        leftPanel.add(lineLabel);

        // Right side
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        JLabel timeLabel = new JLabel("⏰ Mon - Sat 6:00 - 16:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));

        JButton userButton = new JButton("จิรนันต์ เจียงบัติ ▼");
        userButton.setBackground(Color.WHITE);
        userButton.setForeground(new Color(0, 204, 204));
        userButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        userButton.setFocusPainted(false);
        userButton.setBorderPainted(false);
        userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userButton.setPreferredSize(new Dimension(150, 30));

        rightPanel.add(timeLabel);
        rightPanel.add(userButton);

        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("รายการซักผ้า");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));

        panel.add(titleLabel);

        // Service Items
        panel.add(createServiceItem("เสื้อ", "👕", 40));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createServiceItem("กางเกง", "🩳", 40));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createServiceItem("เสื้อคลุม", "🧥", 40));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createServiceItem("เครื่องนอน", "🛏️", 40));

        return panel;
    }

    private JPanel createServiceItem(String itemName, String emoji, int price) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                g2.dispose();
                super.paintComponent(g);
            }
        };

        panel.setLayout(new BorderLayout(15, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setMaximumSize(new Dimension(700, 100));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left - Icon and Name
        JPanel leftInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftInfo.setOpaque(false);

        JLabel iconLabel = new JLabel(emoji);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setOpaque(false);

        JLabel nameLabel = new JLabel(itemName);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

        JLabel priceLabel = new JLabel(price + " บาท");
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        priceLabel.setForeground(Color.GRAY);

        namePanel.add(nameLabel);
        namePanel.add(priceLabel);

        leftInfo.add(iconLabel);
        leftInfo.add(namePanel);

        // Right - Service Type and Quantity Controls
        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightControls.setOpaque(false);

        // Dropdown for service type
        String[] serviceTypes = {"ซัก", "ซักแห้ง", "ซักและรีด"};
        JComboBox<String> serviceCombo = new JComboBox<>(serviceTypes);
        serviceCombo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        serviceCombo.setPreferredSize(new Dimension(120, 35));

        // Quantity controls
        JButton minusBtn = createRoundButton("-");
        JLabel quantityLabel = new JLabel("0");
        quantityLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        quantityLabel.setPreferredSize(new Dimension(30, 30));
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton plusBtn = createRoundButton("+");

        // Add button
        JButton addBtn = new JButton("เพิ่ม");
        addBtn.setBackground(new Color(0, 204, 204));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(80, 35));

        // Button actions
        plusBtn.addActionListener(e -> {
            int current = Integer.parseInt(quantityLabel.getText());
            quantityLabel.setText(String.valueOf(current + 1));
        });

        minusBtn.addActionListener(e -> {
            int current = Integer.parseInt(quantityLabel.getText());
            if (current > 0) {
                quantityLabel.setText(String.valueOf(current - 1));
            }
        });

        addBtn.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) {
                String serviceType = (String) serviceCombo.getSelectedItem();
                addToOrder(itemName, serviceType, quantity, price);
                quantityLabel.setText("0");
                JOptionPane.showMessageDialog(this,
                        "เพิ่ม " + itemName + " (" + serviceType + ") จำนวน " + quantity + " ชิ้นแล้ว",
                        "สำเร็จ",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "กรุณาเลือกจำนวนมากกว่า 0",
                        "แจ้งเตือน",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        rightControls.add(serviceCombo);
        rightControls.add(minusBtn);
        rightControls.add(quantityLabel);
        rightControls.add(plusBtn);
        rightControls.add(addBtn);

        panel.add(leftInfo, BorderLayout.WEST);
        panel.add(rightControls, BorderLayout.EAST);

        return panel;
    }

    private JButton createRoundButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(35, 35));
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setBackground(new Color(230, 230, 230));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(230, 230, 230));
            }
        });

        return btn;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 600));
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel titleLabel = new JLabel("สรุปรายการ");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

        // Summary Panel (scrollable)
        summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(summaryPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Bottom panel with total and clear button
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setBackground(new Color(250, 250, 250));
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        totalLabel = new JLabel("ยอดรวม: 0 บาท");
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        totalLabel.setForeground(new Color(0, 150, 150));

        JButton clearBtn = new JButton("ล้างรายการ");
        clearBtn.setBackground(new Color(231, 76, 60));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        clearBtn.setFocusPainted(false);
        clearBtn.setBorderPainted(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.setPreferredSize(new Dimension(120, 40));

        clearBtn.addActionListener(e -> clearAllOrders());

        bottomPanel.add(totalLabel, BorderLayout.NORTH);
        bottomPanel.add(clearBtn, BorderLayout.SOUTH);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addToOrder(String itemName, String serviceType, int quantity, int pricePerItem) {
        OrderItem item = new OrderItem(itemName, serviceType, quantity, pricePerItem);
        String key = item.getKey();

        if (orderItems.containsKey(key)) {
            // ถ้ามีอยู่แล้ว เพิ่มจำนวน
            OrderItem existing = orderItems.get(key);
            existing.quantity += quantity;
        } else {
            // ถ้ายังไม่มี เพิ่มใหม่
            orderItems.put(key, item);
        }

        updateSummary();
    }

    private void updateSummary() {
        summaryPanel.removeAll();
        totalAmount = 0;

        for (OrderItem item : orderItems.values()) {
            summaryPanel.add(createSummaryItem(item));
            summaryPanel.add(Box.createVerticalStrut(10));
            totalAmount += item.getTotalPrice();
        }

        totalLabel.setText("ยอดรวม: " + totalAmount + " บาท");
        summaryPanel.revalidate();
        summaryPanel.repaint();
    }

    private JPanel createSummaryItem(OrderItem item) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setMaximumSize(new Dimension(300, 100));

        // Left - Item info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(item.itemName);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel serviceLabel = new JLabel(item.serviceType);
        serviceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        serviceLabel.setForeground(Color.GRAY);

        infoPanel.add(nameLabel);
        infoPanel.add(serviceLabel);

        // Right - Quantity and price
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        JLabel quantityLabel = new JLabel(item.serviceType + " " + item.quantity);
        quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        quantityLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel priceLabel = new JLabel(item.getTotalPrice() + " บาท");
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        priceLabel.setForeground(new Color(0, 150, 150));
        priceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(quantityLabel);
        rightPanel.add(priceLabel);

        panel.add(infoPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private void clearAllOrders() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "ต้องการล้างรายการทั้งหมดใช่หรือไม่?",
                "ยืนยัน",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            orderItems.clear();
            updateSummary();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LaundryServiceFrame frame = new LaundryServiceFrame();
            frame.setVisible(true);
        });
    }
}