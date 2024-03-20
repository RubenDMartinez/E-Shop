package monografia.eshop.e_shop.Fragments.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import monografia.eshop.e_shop.databinding.FragmentCrearTiendaBinding;
import monografia.eshop.e_shop.databinding.FragmentMiPerfilBinding;

public class FragmentMiPerfil extends Fragment {

    private FragmentMiPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MiPerfilViewModel miPerfilViewModel =
                new ViewModelProvider(this).get(MiPerfilViewModel.class);

        binding = FragmentMiPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        miPerfilViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}