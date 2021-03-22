package android.exercise.mini.interactions;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity {

  private FloatingActionButton fabStartEdit;
  private FloatingActionButton fabEditDone;
  private TextView textViewTitle;
  private EditText editTextTitle;
  private boolean isEditing;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_edit_title);

    // find all views
    fabStartEdit = this.findViewById(R.id.fab_start_edit);
    fabEditDone = this.findViewById(R.id.fab_edit_done);
    textViewTitle = this.findViewById(R.id.textViewPageTitle);
    editTextTitle = this.findViewById(R.id.editTextPageTitle);

    // setup - start from static title with "edit" button
    fabStartEdit.setVisibility(View.VISIBLE);
    fabEditDone.setVisibility(View.GONE);
    textViewTitle.setText("Page title here");
    textViewTitle.setVisibility(View.VISIBLE);
    editTextTitle.setText("Page title here");
    editTextTitle.setVisibility(View.GONE);

    // handle clicks on "start edit"
    this.fabStartEdit.setOnClickListener(v -> {
      isEditing = true;
      this.switchFabs(fabEditDone, fabStartEdit);
      textViewTitle.setVisibility(View.GONE);
      editTextTitle.setVisibility(View.VISIBLE);
      KeyboardUtils.showKeyboard(this);
      editTextTitle.requestFocus();
      editTextTitle.selectAll();
    });

    // handle clicks on "done edit"
    this.fabEditDone.setOnClickListener(v -> {
      this.switchFabs(fabStartEdit, fabEditDone);
      textViewTitle.setText(this.editTextTitle.getText().toString());
      editTextTitle.setVisibility(View.GONE);
      textViewTitle.setVisibility(View.VISIBLE);
      KeyboardUtils.hideKeyboard(this);
      isEditing = false;
    });
  }

  @Override
  public void onBackPressed() {
    // BACK button was clicked

    if (isEditing) {
      editTextTitle.setVisibility(View.GONE);
      textViewTitle.setVisibility(View.VISIBLE);
      switchFabs(this.fabStartEdit, this.fabEditDone);
      isEditing = false;
    } else {
      super.onBackPressed();
    }
  }

  private void switchFabs(final FloatingActionButton showFab, final FloatingActionButton hideFab) {
    hideFab.addOnHideAnimationListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(final Animator animator) {
      }

      @Override
      public void onAnimationCancel(final Animator animator) {
      }

      @Override
      public void onAnimationRepeat(final Animator animator) {
      }

      @Override
      public void onAnimationEnd(final Animator animator) {
        showFab.show();
        hideFab.removeOnHideAnimationListener(this);
      }
    });
    hideFab.hide();
  }

}