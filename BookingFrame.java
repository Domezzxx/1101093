import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingFrame extends JFrame {
    private List<OrderItem> orderItems;
    private int totalAmount;
    private JPanel itemsPanel;
    private JLabel totalLabel;
    private LocalDate selectedDate;
    private int selectedHour = 6;
    private int selectedMinute = 28;
    private String selectedPeriod = "PM";

    // คลาสเก็บข้อมูลรายการสั่งซัก
    static class OrderItem {
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
    }

    public BookingFrame(List<OrderItem> items, int total) {
        this.orderItems = items != null ? items : new ArrayList<>();
        this.totalAmount = total;
        this.selectedDate = LocalDate.now();
        initComponents();
    }

    private void initComponents() {
        setTitle("เลือกการชำระเงิน");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Bar
        JPanel topBar = createTopBar();

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Left Panel - รายการค่า
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 20);
        mainPanel.add(createLeftPanel(), gbc);

        // Right Panel - วันที่ต้องการรวง และ เวลาที่ต้องการรวง
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        mainPanel.add(createRightPanel(), gbc);

        add(topBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0, 204, 204));
        topBar.setPreferredSize(new Dimension(1100, 50));
        topBar.setBorder(new EmptyBorder(8, 20, 8, 20));

        JButton backButton = new JButton("< เลือกการชำระเงิน");
        backButton.setBackground(new Color(0, 204, 204));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> this.dispose());

        topBar.add(backButton, BorderLayout.WEST);

        return topBar;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("รายการสินค้า");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel);

        // Items Panel (Scrollable)
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(Color.WHITE);

        // Add all order items
        for (OrderItem item : orderItems) {
            itemsPanel.add(createOrderItemPanel(item));
            itemsPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(450, 400));
        scrollPane.setPreferredSize(new Dimension(450, 400));

        panel.add(scrollPane);

        return panel;
    }

    private JPanel createOrderItemPanel(OrderItem item) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(450, 80));

        // Left - Item name and service type with dropdown
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(item.itemName);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        // Service type dropdown
        String[] serviceTypes = {"ซัก", "ซักแห้ง", "ซักและรีด"};
        JComboBox<String> serviceCombo = new JComboBox<>(serviceTypes);
        serviceCombo.setSelectedItem(item.serviceType);
        serviceCombo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        serviceCombo.setMaximumSize(new Dimension(120, 30));
        serviceCombo.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(serviceCombo);

        // Center - Quantity controls
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        centerPanel.setOpaque(false);

        JButton minusBtn = createSmallButton("-");
        JLabel qtyLabel = new JLabel(String.valueOf(item.quantity));
        qtyLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        qtyLabel.setPreferredSize(new Dimension(30, 30));
        qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton plusBtn = createSmallButton("+");

        minusBtn.addActionListener(e -> {
            if (item.quantity > 1) {
                item.quantity--;
                qtyLabel.setText(String.valueOf(item.quantity));
                updateTotal();
            }
        });

        plusBtn.addActionListener(e -> {
            item.quantity++;
            qtyLabel.setText(String.valueOf(item.quantity));
            updateTotal();
        });

        centerPanel.add(minusBtn);
        centerPanel.add(qtyLabel);
        centerPanel.add(plusBtn);

        // Right - Price
        JLabel priceLabel = new JLabel("฿" + item.getTotalPrice());
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(priceLabel, BorderLayout.EAST);

        return panel;
    }

    private JButton createSmallButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(30, 30));
        btn.setFont(new Font("Tahoma", Font.BOLD, 16));
        btn.setBackground(new Color(240, 240, 240));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Date Picker Section
        JLabel dateTitle = new JLabel("วันที่ต้องการจอง");
        dateTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        dateTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel calendarPanel = createCalendarPanel();
        calendarPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(dateTitle);
        panel.add(Box.createVerticalStrut(15));
        panel.add(calendarPanel);
        panel.add(Box.createVerticalStrut(30));

        // Time Picker Section
        JLabel timeTitle = new JLabel("เวลาที่ต้องการจอง");
        timeTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        timeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel timePanel = createTimePickerPanel();
        timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(timeTitle);
        panel.add(Box.createVerticalStrut(15));
        panel.add(timePanel);
        panel.add(Box.createVerticalStrut(30));

        // Total and Submit Section
        JPanel bottomPanel = createBottomPanel();
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(bottomPanel);

        return panel;
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 7, 8, 8));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(420, 180));
        panel.setPreferredSize(new Dimension(420, 180));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Day headers
        String[] days = {"อา", "จ", "อ", "พ", "พฤ", "ศ", "ส"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Tahoma", Font.BOLD, 13));
            label.setForeground(Color.GRAY);
            panel.add(label);
        }

        // Calculate dates for next 14 days
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(today.getDayOfWeek().getValue() % 7); // Start from Sunday

        for (int i = 0; i < 14; i++) {
            LocalDate date = startDate.plusDays(i);
            boolean isToday = date.equals(today);
            boolean isSelectable = !date.isBefore(today) && date.isBefore(today.plusDays(15));

            JButton dayBtn = createDayButton(date, isToday, isSelectable);
            panel.add(dayBtn);
        }

        return panel;
    }

    private JButton createDayButton(LocalDate date, boolean isToday, boolean isSelectable) {
        JButton btn = new JButton(String.valueOf(date.getDayOfMonth()));
        btn.setFont(new Font("Tahoma", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        if (!isSelectable) {
            btn.setEnabled(false);
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(200, 200, 200));
        } else if (date.equals(selectedDate)) {
            btn.setBackground(new Color(0, 204, 204));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> {
                selectedDate = date;
                updateCalendar();
            });
        }

        return btn;
    }

    private void updateCalendar() {
        // Recreate the calendar panel (simplified approach)
        Container parent = getContentPane();
        revalidate();
        repaint();
    }

    private JPanel createTimePickerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(420, 150));
        panel.setPreferredSize(new Dimension(420, 150));
        panel.setBorder(new EmptyBorder(20, 15, 20, 15));

        // Time display
        JPanel timeDisplay = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        timeDisplay.setBackground(Color.WHITE);

        JLabel hourLabel = new JLabel(String.format("%02d", selectedHour));
        hourLabel.setFont(new Font("Tahoma", Font.BOLD, 36));

        JLabel colonLabel = new JLabel(":");
        colonLabel.setFont(new Font("Tahoma", Font.BOLD, 36));

        JLabel minuteLabel = new JLabel(String.format("%02d", selectedMinute));
        minuteLabel.setFont(new Font("Tahoma", Font.BOLD, 36));

        JLabel periodLabel = new JLabel(selectedPeriod);
        periodLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

        timeDisplay.add(hourLabel);
        timeDisplay.add(colonLabel);
        timeDisplay.add(minuteLabel);
        timeDisplay.add(periodLabel);

        // Time controls
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controls.setBackground(Color.WHITE);

        JButton hourUpBtn = createTimeButton("▲");
        JButton hourDownBtn = createTimeButton("▼");
        JButton minUpBtn = createTimeButton("▲");
        JButton minDownBtn = createTimeButton("▼");
        JButton periodBtn = createTimeButton("AM/PM");

        hourUpBtn.addActionListener(e -> {
            selectedHour = (selectedHour % 12) + 1;
            hourLabel.setText(String.format("%02d", selectedHour));
        });

        hourDownBtn.addActionListener(e -> {
            selectedHour = selectedHour == 1 ? 12 : selectedHour - 1;
            hourLabel.setText(String.format("%02d", selectedHour));
        });

        minUpBtn.addActionListener(e -> {
            selectedMinute = (selectedMinute + 1) % 60;
            minuteLabel.setText(String.format("%02d", selectedMinute));
        });

        minDownBtn.addActionListener(e -> {
            selectedMinute = selectedMinute == 0 ? 59 : selectedMinute - 1;
            minuteLabel.setText(String.format("%02d", selectedMinute));
        });

        periodBtn.addActionListener(e -> {
            selectedPeriod = selectedPeriod.equals("AM") ? "PM" : "AM";
            periodLabel.setText(selectedPeriod);
        });

        JPanel hourControls = new JPanel();
        hourControls.setLayout(new BoxLayout(hourControls, BoxLayout.Y_AXIS));
        hourControls.setOpaque(false);
        hourUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        hourDownBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        hourControls.add(hourUpBtn);
        hourControls.add(Box.createVerticalStrut(30));
        hourControls.add(hourDownBtn);

        JPanel minControls = new JPanel();
        minControls.setLayout(new BoxLayout(minControls, BoxLayout.Y_AXIS));
        minControls.setOpaque(false);
        minUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        minDownBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        minControls.add(minUpBtn);
        minControls.add(Box.createVerticalStrut(30));
        minControls.add(minDownBtn);

        controls.add(hourControls);
        controls.add(Box.createHorizontalStrut(30));
        controls.add(minControls);
        controls.add(Box.createHorizontalStrut(20));
        controls.add(periodBtn);

        panel.add(timeDisplay);
        panel.add(controls);

        return panel;
    }

    private JButton createTimeButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(50, 30));
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setBackground(new Color(230, 230, 230));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(250, 250, 250));
        panel.setMaximumSize(new Dimension(420, 100));

        // Total amount
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(new Color(250, 250, 250));

        JLabel totalTextLabel = new JLabel("รวมทั้งหมด");
        totalTextLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        totalLabel = new JLabel("฿" + totalAmount);
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        totalLabel.setForeground(new Color(0, 150, 150));

        totalPanel.add(totalTextLabel, BorderLayout.WEST);
        totalPanel.add(totalLabel, BorderLayout.EAST);

        // Submit button
        JButton submitBtn = new JButton("ยืนยัน");
        submitBtn.setBackground(new Color(0, 204, 204));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
        submitBtn.setFocusPainted(false);
        submitBtn.setBorderPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(420, 50));

        submitBtn.addActionListener(e -> {
            String dateStr = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String timeStr = String.format("%02d:%02d %s", selectedHour, selectedMinute, selectedPeriod);

            JOptionPane.showMessageDialog(this,
                    "จองสำเร็จ!\n" +
                            "วันที่: " + dateStr + "\n" +
                            "เวลา: " + timeStr + "\n" +
                            "ยอดรวม: ฿" + totalAmount,
                    "ยืนยันการจอง",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(totalPanel, BorderLayout.NORTH);
        panel.add(submitBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void updateTotal() {
        totalAmount = 0;
        for (OrderItem item : orderItems) {
            totalAmount += item.getTotalPrice();
        }
        totalLabel.setText("฿" + totalAmount);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // ตัวอย่างข้อมูล
            List<OrderItem> items = new ArrayList<>();
            items.add(new OrderItem("เสื้อ", "ซัก", 2, 40));
            items.add(new OrderItem("กางเกง", "ซัก", 1, 40));
            items.add(new OrderItem("เสื้อคลุม", "ซัก", 1, 40));
            items.add(new OrderItem("เครื่องนอน", "ซัก", 1, 40));

            BookingFrame frame = new BookingFrame(items, 200);
            frame.setVisible(true);
        });
    }
}