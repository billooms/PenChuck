
package billooms.penchuck.rosetteeditor;

import billooms.penchuck.rosettemodel.api.Rosette;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This JPanel edits the parameters of a rosette.
 * Note: the rosette is owned by this JPanel and is static
 * @author Bill Ooms Copyright (c) 2010 Studio of Bill Ooms all rights reserved
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class RosetteEditorPanel extends JPanel {

	private Rosette rosette;

    /** Creates new form RosetteEditorPanel */
    public RosetteEditorPanel(Rosette rosette) {
		this.rosette = rosette;

        initComponents();

		Hashtable labelTable = new Hashtable();		// labels for ampSlider
		labelTable.put(0, new JLabel("0"));
		labelTable.put(25, new JLabel("0.25"));
		labelTable.put(50, new JLabel("0.5"));
		labelTable.put(75, new JLabel("0.75"));
		labelTable.put(100, new JLabel("1.0"));
		ampSlider.setLabelTable(labelTable);
		for (int i = 1; i < Rosette.Styles.values().length; i++) {	// Don't use Styles.NONE
            styleCombo.addItem(Rosette.Styles.values()[i].text);
		}

		updateForm();
    }

	private void updateRosette() {
		rosette.setPToP(((Number)ampField.getValue()).doubleValue());
		ampSlider.setValue((int)(100.0*rosette.getPToP()));
		rosette.setPhase(((Number)phaseField.getValue()).doubleValue());
		phaseSlider.setValue((int)rosette.getPhase());
		rosette.setRepeat(((Number) repeatSpinner.getValue()).intValue());
		rosette.setStyle(styleCombo.getSelectedIndex()+1);
		updateForm();		// in case rosette limits some parameter (like repeat)
	}

	private void updateForm() {
		ampField.setValue(rosette.getPToP());
		ampSlider.setValue((int)(100.0*rosette.getPToP()));
		phaseField.setValue(rosette.getPhase());
		phaseSlider.setValue((int)rosette.getPhase());
		repeatSpinner.setValue(rosette.getRepeat());
		styleCombo.setSelectedIndex(rosette.getStyle().ordinal()-1);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ampField = new javax.swing.JFormattedTextField();
        ampSlider = new javax.swing.JSlider();
        jPanel2 = new javax.swing.JPanel();
        phaseField = new javax.swing.JFormattedTextField();
        phaseSlider = new javax.swing.JSlider();
        repeatSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        styleCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.jPanel1.border.title"))); // NOI18N

        ampField.setColumns(5);
        ampField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.000"))));
        ampField.setText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.ampField.text")); // NOI18N
        ampField.setToolTipText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.ampField.toolTipText")); // NOI18N
        ampField.setValue(Rosette.DEFAULT_PTOP);
        ampField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeAmpField(evt);
            }
        });

        ampSlider.setMajorTickSpacing(25);
        ampSlider.setMinorTickSpacing(5);
        ampSlider.setPaintLabels(true);
        ampSlider.setPaintTicks(true);
        ampSlider.setToolTipText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.ampSlider.toolTipText")); // NOI18N
        ampSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                changeAmpSlider(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(ampField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(ampSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(ampField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ampSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.jPanel2.border.title"))); // NOI18N

        phaseField.setColumns(5);
        phaseField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        phaseField.setText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.phaseField.text")); // NOI18N
        phaseField.setToolTipText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.phaseField.toolTipText")); // NOI18N
        phaseField.setValue(Rosette.DEFAULT_PHASE);
        phaseField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePhaseField(evt);
            }
        });

        phaseSlider.setMajorTickSpacing(90);
        phaseSlider.setMaximum(360);
        phaseSlider.setMinorTickSpacing(15);
        phaseSlider.setPaintLabels(true);
        phaseSlider.setPaintTicks(true);
        phaseSlider.setToolTipText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.phaseSlider.toolTipText")); // NOI18N
        phaseSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                changePhaseSlider(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(phaseField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(phaseSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(phaseField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(phaseSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        repeatSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        repeatSpinner.setToolTipText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.repeatSpinner.toolTipText")); // NOI18N
        repeatSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                repeatChanged(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.jLabel1.text")); // NOI18N

        styleCombo.setToolTipText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.styleCombo.toolTipText")); // NOI18N
        styleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeStyle(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(RosetteEditorPanel.class, "RosetteEditorPanel.jLabel2.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(repeatSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(styleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                .add(layout.createSequentialGroup()
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(layout.createSequentialGroup()
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(styleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(repeatSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel1))))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void changeAmpSlider(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changeAmpSlider
//		if (ampSlider.isFocusOwner()) {
			ampField.setValue(ampSlider.getValue() / 100.0);
			updateRosette();
//		}
	}//GEN-LAST:event_changeAmpSlider

	private void changePhaseSlider(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_changePhaseSlider
//		if (phaseSlider.isFocusOwner()) {
			phaseField.setValue(phaseSlider.getValue());
			updateRosette();
//		}
	}//GEN-LAST:event_changePhaseSlider

	private void changeAmpField(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeAmpField
		if (ampField.isFocusOwner()) {
			updateRosette();
		}
	}//GEN-LAST:event_changeAmpField

	private void changePhaseField(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePhaseField
		if (phaseField.isFocusOwner()) {
			updateRosette();
		}
	}//GEN-LAST:event_changePhaseField

	private void changeStyle(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeStyle
		if (styleCombo.isFocusOwner()) {
			updateRosette();
		}
	}//GEN-LAST:event_changeStyle

	private void repeatChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_repeatChanged
		updateRosette();
	}//GEN-LAST:event_repeatChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField ampField;
    private javax.swing.JSlider ampSlider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JFormattedTextField phaseField;
    private javax.swing.JSlider phaseSlider;
    private javax.swing.JSpinner repeatSpinner;
    private javax.swing.JComboBox styleCombo;
    // End of variables declaration//GEN-END:variables

}
